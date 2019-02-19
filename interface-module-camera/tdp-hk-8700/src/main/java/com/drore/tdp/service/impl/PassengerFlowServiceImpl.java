package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.domain.Pagination;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.ThirdPassengerFlowRecord;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.constant.SyncTimeCode;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.flow.PassengerFlowDevice;
import com.drore.tdp.domain.flow.PassengerFlowRecord;
import com.drore.tdp.domain.table.Table;
import com.drore.tdp.utils.Hk8700Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.drore.tdp.constant.Hk8700Constant.SUCCESS_RESPONSE;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  11:30.
 */
@Slf4j
@Service
public class PassengerFlowServiceImpl extends BaseApiService {
    @Value("${tdp.params.host}")
    private String host;
    @Value("${tdp.params.appKey}")
    private String appKey;
    @Value("${tdp.params.secret}")
    private String secret;
    @Autowired
    private CloudQueryRunner runner;
    @Autowired
    private QueryUtil queryUtil;
    @Value("${tdp.first-sync-time.passenger-flow-record}")
    private String firstSyncTime;

    public ResponseBase syncPassengerFlowRecord() {
        List<PassengerFlowDevice> passengerFlowDevices = passengerFlowDeviceList();
        List<String> listCameraUuid = passengerFlowDevices.stream().map(passengerFlowDevice -> passengerFlowDevice.getDeviceNo()).collect(Collectors.toList());
        String startTime = queryUtil.getSyncTime(SyncTimeCode.PASSENGER_FLOE_RECORD);
        String userUuid = Hk8700Util.getDefaultUserUuid(host, appKey, secret);
        return passengerFlowRecord(host, appKey, secret, userUuid, listCameraUuid, startTime);
    }


    public ResponseBase passengerFlowRecord(String host, String appKey, String secret, String defaultUuid, List<String> listCameraUuid, String startTime) {
        String path = Hk8700Constant.GET_FOOTFALL_DATA;
        JSONObject param = new JSONObject();
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        String endTime;
        String nowTime = DateTimeUtil.nowDateString();
        try {
            do {
                if (StringUtils.isEmpty(startTime)) {
                    startTime = firstSyncTime;
                } else {
                    startTime = DateTimeUtil.stringPlusSeconds(startTime, 1);
                }
                endTime = DateTimeUtil.stringPlusDays(startTime, 1);
                if (endTime.compareTo(nowTime) > 0) {
                    endTime = nowTime;
                }
                //时间转换成毫秒
                param.put("footfallStartTime", DateTimeUtil.stringToTimestamp(startTime));
                param.put("footfallEndTime", DateTimeUtil.stringToTimestamp(endTime));
                for (int i = 0; i < listCameraUuid.size(); i++) {
                    String cameraUuid = listCameraUuid.get(i);
                    CameraDevice cameraDevice = getCameraDeviceByCameraUuid(cameraUuid);
                    param.put("time", System.currentTimeMillis());
                    param.put("cameraUuid", cameraUuid);
                    String buildToken = Hk8700Util.postBuildToken(host, path, param, secret);
                    JSONObject response = HttpClientUtil.httpPost(buildToken, param);
                    if (response == null) {
                        break;
                    }
                    Integer errorCode = response.getInteger("errorCode");
                    String errorMessage = response.getString("errorMessage");
                    if (!Objects.equals(SUCCESS_RESPONSE, errorCode)) {
                        log.error("接口响应结果错误:{}", errorMessage);
                        break;
                    }
                    JSONArray data = response.getJSONArray("data");
                    if (CollectionUtils.isEmpty(data)) {
                        log.info("{}-{} 客流监控点:{},设备IP地址:{},设备名称:{} 数据不存在", startTime, endTime, cameraUuid, cameraDevice.getDeviceIp(), cameraDevice.getDeviceName());
                        continue;
                    }
                    List<ThirdPassengerFlowRecord> thirdPassengerFlowRecords = JSONArray.parseArray(JSON.toJSONString(data), ThirdPassengerFlowRecord.class);
                    List<PassengerFlowRecord> passengerFlowRecords = thirdPassengerFlowRecords.stream().map(thirdPassengerFlowRecord -> {
                        PassengerFlowRecord passengerFlowRecord = new PassengerFlowRecord();
                        passengerFlowRecord.setDeviceNo(cameraUuid);
                        passengerFlowRecord.setDeviceName(cameraDevice.getDeviceName());
                        passengerFlowRecord.setStartTime(thirdPassengerFlowRecord.getFootfallStartTime());
                        passengerFlowRecord.setEndTime(thirdPassengerFlowRecord.getFootfallEndTime());
                        passengerFlowRecord.setEnterNumber(thirdPassengerFlowRecord.getPassengersIn());
                        passengerFlowRecord.setLeaveNumber(thirdPassengerFlowRecord.getPassengersOut());
                        return passengerFlowRecord;
                    }).collect(Collectors.toList());
                    queryUtil.savePassengerFlowRecord(passengerFlowRecords);
                }
            } while (endTime.compareTo(nowTime) < 0);
            Map map = new HashMap(2);
            map.put("code", SyncTimeCode.PASSENGER_FLOE_RECORD);
            map.put("sync_time", endTime);
            queryUtil.saveOrUpdateSyncTime(map);
            return success();
        } catch (Exception e) {
            return error();
        }
    }

    /**
     * 获取所有监控客流设备信息
     *
     * @return
     */
    private List<PassengerFlowDevice> passengerFlowDeviceList() {
        Pagination<PassengerFlowDevice> pagination = runner.queryListByExample(PassengerFlowDevice.class, Table.PASSENGER_FLOW_DEVICE, 1, 1000);
        if (pagination != null && pagination.getCount() > 0) {
            return pagination.getData();
        } else {
            log.info("未获取到监控客流设备信息");
            return null;
        }
    }

    /**
     * 根据监控点id获取监控点信息
     *
     * @param cameraUuid
     * @return
     */
    private CameraDevice getCameraDeviceByCameraUuid(String cameraUuid) {
        Map map = new HashMap(1);
        map.put("index_code", cameraUuid);
        Pagination<CameraDevice> pagination = runner.queryListByExample(CameraDevice.class, Table.CAMERA_DEVICE, map);
        if (pagination != null && pagination.getCount() > 0) {
            return pagination.getData().get(0);
        } else {
            log.info("客流监控点:{} 未匹配到对应的IP地址", cameraUuid);
            return null;
        }
    }
}
