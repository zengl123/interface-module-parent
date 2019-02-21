package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.domain.Pagination;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.ThirdPassengerFlowRecord;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
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

    /**
     * 获取客流原始数据
     *
     * @param host
     * @param appKey
     * @param secret
     * @param defaultUuid
     * @param listCameraUuid
     * @param startTime
     * @return
     */
    public ResponseBase passengerFlowRecord(String host, String appKey, String secret, String defaultUuid, List<String> listCameraUuid, String startTime) {
        String path = Hk8700Constant.GET_FOOTFALL_DATA;
        JSONObject param = new JSONObject();
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        String endTime;
        String nowTime = DateTimeUtil.nowDateTimeString();
        boolean flag = true;
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
                List<PassengerFlowRecord> all = new ArrayList<>();
                for (int i = 0; i < listCameraUuid.size(); i++) {
                    String cameraUuid = listCameraUuid.get(i);
                    CameraDevice cameraDevice = queryUtil.getCameraDeviceByCameraUuid(cameraUuid);
                    if (cameraDevice == null) {
                        flag = false;
                        break;
                    }
                    param.put("time", System.currentTimeMillis());
                    param.put("cameraUuid", cameraUuid);
                    String buildToken = Hk8700Util.postBuildToken(host, path, param, secret);
                    JSONObject response = HttpClientUtil.httpPost(buildToken, param);
                    if (response == null) {
                        flag = false;
                        break;
                    }
                    Integer errorCode = response.getInteger("errorCode");
                    String errorMessage = response.getString("errorMessage");
                    if (!Objects.equals(SUCCESS_RESPONSE, errorCode)) {
                        log.error("接口响应结果错误:{}", errorMessage);
                        flag = false;
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
                    all.addAll(passengerFlowRecords);
                }
                //
                ResponseBase responseBase;
                if (CollectionUtils.isNotEmpty(all)) {
                    responseBase = queryUtil.savePassengerFlowRecord(all);
                    //保存成功进行下一次请求
                    if (responseBase.isStatus()) {
                        startTime = endTime;
                    } else {
                        flag = false;
                        break;
                    }
                } else {
                    log.info("{}-{} 所有客流监控点未获取到数据", startTime, endTime);
                }
                if (flag) {
                    Map map = new HashMap(2);
                    map.put("code", SyncTimeCode.PASSENGER_FLOE_RECORD);
                    map.put("sync_time", endTime);
                    responseBase = queryUtil.saveOrUpdateSyncTime(map);
                    if (responseBase.isStatus()) {
                        log.info("客流原始数据 {}-{} {}", startTime, endTime, responseBase.getMessage());
                    } else {
                        log.error("客流原始数据 {}-{} {}", startTime, endTime, responseBase.getMessage());
                        break;
                    }
                } else {
                    break;
                }
            } while (endTime.compareTo(nowTime) < 0);
            if (flag) {
                return success();
            } else {
                return error();
            }
        } catch (Exception e) {
            log.error("获取客流原始数据异常 {}", e);
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
}
