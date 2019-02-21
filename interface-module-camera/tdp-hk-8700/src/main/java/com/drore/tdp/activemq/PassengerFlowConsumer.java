package com.drore.tdp.activemq;


import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.EventDis;
import com.drore.tdp.bo.ThirdPassengerFlowMq;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.redis.RedisKey;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.flow.PassengerFlowDevice;
import com.drore.tdp.domain.flow.PassengerFlowRecordV2;
import com.drore.tdp.utils.Hk8700Util;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import java.util.Objects;

import static com.drore.tdp.common.redis.RedisKey.EXPIRE_TIME_70;

/**
 * 描述:客流mq数据对接
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  17:13.
 */
@Slf4j
@Component
public class PassengerFlowConsumer {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private QueryUtil queryUtil;

    @JmsListener(destination = "${tdp.hk.common-destination}")
    public void getMessage(BytesMessage message) {
        EventDis.CommEventLog parseFrom;
        try {
            long length = message.getBodyLength();
            byte[] bt = new byte[(int) length];
            // 将BytesMessage转换为byte类型
            message.readBytes(bt);
            // 壳文件字段，EventDis类为event_dis.proto文件解析而来，CommEventLog类为事件壳文件类
            parseFrom = EventDis.CommEventLog.parseFrom(bt);
        } catch (JMSException e) {
            log.error("JMSException {}", e);
            return;
        } catch (InvalidProtocolBufferException e) {
            log.error("InvalidProtocolBufferException {}", e);
            return;
        }
        int eventType = parseFrom.getEventType();
        if (Hk8700Constant.EVENT_TYPE_PASSENGER_FLOW != eventType) {
            //其他事件码
            return;
        }
        String extInfo = parseFrom.getExtInfo().toStringUtf8();
        JSONObject jsonObject = XmlUtil.xml2json(extInfo);
        ThirdPassengerFlowMq thirdPassengerFlowMq = jsonObject.getObject("ExtEventInfo", ThirdPassengerFlowMq.class);
        //客流监控点id
        String sourceIdx = parseFrom.getSourceIdx();
        CameraDevice cameraDevice = queryUtil.getCameraDeviceByCameraUuid(sourceIdx);
        //根据sourceIdx未找到对应的监控点
        if (Objects.isNull(cameraDevice)) {
            log.error("客流监控设备编号{} 未匹配到监控点,请在海康平台配置");
            return;
        }
        PassengerFlowDevice passengerFlowDevice = new PassengerFlowDevice();
        //组织名称
        String regionIdx = parseFrom.getRegionIdx();
        passengerFlowDevice.setParentNo(regionIdx);
        passengerFlowDevice.setDeviceNo(sourceIdx);
        //客流监控点名称
        String sourceName = parseFrom.getSourceName();
        passengerFlowDevice.setDeviceName(sourceName);
        String deviceIp = cameraDevice.getDeviceIp();
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
        log.debug("监控客流数据 监控点名称 {} 上传时间 {}", sourceName, startTime, endTime);
        try {
            redisUtil.set(RedisKey.PASSENGER_FLOW_INFO.concat(regionIdx), passengerFlowRecordV2, EXPIRE_TIME_70);
        } catch (Exception e) {
            log.error("监控客流数据缓存异常 {}-{} {}-{} ", startTime, endTime, regionIdx, sourceName);
        }
        //存储
    }
}
