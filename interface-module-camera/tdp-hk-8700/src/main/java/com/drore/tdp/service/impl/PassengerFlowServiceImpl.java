package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.domain.Pagination;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.EventDis.CommEventLog;
import com.drore.tdp.bo.ThirdPassengerFlowMq;
import com.drore.tdp.bo.ThirdPassengerFlowRecord;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.constant.SyncTimeCode;
import com.drore.tdp.common.redis.RedisKey;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.flow.PassengerFlowDevice;
import com.drore.tdp.domain.flow.PassengerFlowRecord;
import com.drore.tdp.domain.flow.PassengerFlowRecordV2;
import com.drore.tdp.domain.table.Table;
import com.drore.tdp.utils.Hk8700Util;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.drore.tdp.common.redis.RedisKey.EXPIRE_TIME_70;
import static com.drore.tdp.constant.Hk8700Constant.SUCCESS_RESPONSE;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  11:30.
 */
@Service
public class PassengerFlowServiceImpl extends BaseApiService {
    private Logger log = LoggerFactory.getLogger("passengerFlow");
    @Value("${tdp.hk.host}")
    private String host;
    @Value("${tdp.hk.appKey}")
    private String appKey;
    @Value("${tdp.hk.secret}")
    private String secret;
    @Autowired
    private CloudQueryRunner runner;
    @Autowired
    private QueryUtil queryUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${tdp.first-sync-time.passenger-flow-record}")
    private String firstSyncTime;

    public ResponseBase syncPassengerFlowRecord() {
        List<PassengerFlowDevice> passengerFlowDevices = passengerFlowDeviceList();
        List<String> listCameraUuid = passengerFlowDevices.stream().map(passengerFlowDevice -> passengerFlowDevice.getDeviceNo()).collect(Collectors.toList());
        String startTime = queryUtil.getSyncTime(SyncTimeCode.PASSENGER_FLOE_RECORD);
        startTime = StringUtils.isEmpty(startTime) ? firstSyncTime : startTime;
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
                    CameraDevice cameraDevice = queryUtil.getCameraDeviceByIndexCode(cameraUuid);
                    if (Objects.isNull(cameraDevice)) {
                        flag = false;
                        log.error("客流监控点id {} 未匹配到监控设备信息", cameraUuid);
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
                ResponseBase responseBase;
                if (CollectionUtils.isNotEmpty(all)) {
                    responseBase = queryUtil.savePassengerFlowRecord(all);
                    //保存成功进行下一次请求
                    if (responseBase.isStatus()) {
                        log.debug("新增客流监控数据记录成功,共新增:{}条数据", all.size());
                        startTime = DateTimeUtil.stringPlusSeconds(endTime, 1);
                    } else {
                        flag = false;
                        break;
                    }
                } else {
                    log.info("{}-{} 所有客流监控点未获取到数据", startTime, endTime);
                }
                responseBase = queryUtil.saveOrUpdateSyncTime(SyncTimeCode.PASSENGER_FLOE_RECORD, startTime, "监控客流数据同步时间");
                if (responseBase.isStatus()) {
                    log.debug("客流原始数据同步时间配置 {}-{} {}", startTime, endTime, responseBase.getMessage());
                } else {
                    flag = false;
                    log.error("客流原始数据同步时间配置 {}-{} {}", startTime, endTime, responseBase.getMessage());
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
            log.error("未获取到监控客流设备信息");
            return null;
        }
    }

    public void passengerFlow(CommEventLog commEventLog) {
        String extInfo = commEventLog.getExtInfo().toStringUtf8();
        JSONObject jsonObject = XmlUtil.xml2json(extInfo);
        ThirdPassengerFlowMq thirdPassengerFlowMq = jsonObject.getObject("ExtEventInfo", ThirdPassengerFlowMq.class);
        //客流监控点id
        String sourceIdx = commEventLog.getSourceIdx();
        CameraDevice cameraDevice = queryUtil.getCameraDeviceByIndexCode(sourceIdx);
        //根据sourceIdx未找到对应的监控点
        if (Objects.isNull(cameraDevice)) {
            log.error("客流监控设备id{} 未匹配到监控设备信息", sourceIdx);
            return;
        }
        PassengerFlowDevice passengerFlowDevice = new PassengerFlowDevice();
        //组织名称
        String regionIdx = commEventLog.getRegionIdx();
        passengerFlowDevice.setParentNo(regionIdx);
        passengerFlowDevice.setDeviceNo(sourceIdx);
        //客流监控点名称
        String sourceName = commEventLog.getSourceName();
        passengerFlowDevice.setDeviceName(sourceName);
        String deviceIp = cameraDevice.getDeviceIp() + "_" + cameraDevice.getChannelNo();
        passengerFlowDevice.setDeviceIp(deviceIp);
        //先保存客流监控点
        ResponseBase responseBase = queryUtil.saveOrUpdatePassengerFlowDevice(passengerFlowDevice);
        if (!responseBase.isStatus()) {
            return;
        }
        //客流记录数据封装
        PassengerFlowRecordV2 passengerFlowRecordV2 = new PassengerFlowRecordV2();
        passengerFlowRecordV2.setDeviceNo(sourceIdx);
        passengerFlowRecordV2.setDeviceName(sourceName);
        String startTime = thirdPassengerFlowMq.getStartTime();
        passengerFlowRecordV2.setStartTime(startTime);
        String endTime = thirdPassengerFlowMq.getEndTime();
        passengerFlowRecordV2.setEndTime(endTime);
        Integer enterNum = thirdPassengerFlowMq.getEnterNum();
        passengerFlowRecordV2.setEnterNum(enterNum);
        Integer leaveNum = thirdPassengerFlowMq.getLeaveNum();
        passengerFlowRecordV2.setLeaveNum(leaveNum);
        Integer frmIn = thirdPassengerFlowMq.getFrmIn();
        passengerFlowRecordV2.setTotalIn(frmIn);
        Integer frmOut = thirdPassengerFlowMq.getFrmOut();
        passengerFlowRecordV2.setTotalOut(frmOut);
        passengerFlowRecordV2.setDeviceIp(deviceIp);
        //存redis
        log.debug("监控客流数据 监控点名称 {} 上传时间 {}-{}", sourceName, startTime, endTime);
        try {
            redisUtil.set(RedisKey.PASSENGER_FLOW_INFO.concat(deviceIp), passengerFlowRecordV2, EXPIRE_TIME_70);
        } catch (Exception e) {
            log.error("监控客流数据缓存异常 {}-{} {}-{} ", startTime, endTime, deviceIp, sourceName);
        }
        //存储
    }
}
