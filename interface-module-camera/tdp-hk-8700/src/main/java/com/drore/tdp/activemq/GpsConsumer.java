package com.drore.tdp.activemq;

import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.bo.gps.EventDis;
import com.drore.tdp.bo.ThirdGpsRecord;
import com.drore.tdp.common.redis.RedisKey;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.gps.GpsRecord;
import com.drore.tdp.utils.GpsDataConvertUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import java.util.Objects;

import static com.drore.tdp.common.redis.RedisKey.EXPIRE_TIME_20;

/**
 * 描述:gps模块数据对接
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  13:50.
 */

@Component
public class GpsConsumer {
    private Logger log = LoggerFactory.getLogger("gpsLog");
    @Autowired
    private RedisUtil redisUtil;

    @JmsListener(destination = "${tdp.hk.gps-destination}")
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
        if (Hk8700Constant.EVENT_TYPE_GPS != eventType) {
            //其他事件码
            return;
        }
        String extInfo = parseFrom.getExtInfo().toStringUtf8();
        JSONObject jsonObject = XmlUtil.xml2json(extInfo);
        ThirdGpsRecord thirdGpsRecord = jsonObject.getObject("Params", ThirdGpsRecord.class);
        log.debug("接收到gps数据 设备名称 {} 上传时间 {}", thirdGpsRecord.getDeviceName(), thirdGpsRecord.getTime());
        //数据产生时间
        String time = thirdGpsRecord.getTime();
        Integer latitude = thirdGpsRecord.getLatitude();
        Integer longitude = thirdGpsRecord.getLongitude();
        boolean isPositive = true;
        if (longitude < 0) {
            isPositive = false;
        }
        Double dLongitude = GpsDataConvertUtil.toDoubleDegree(longitude, isPositive);
        if (latitude < 0) {
            isPositive = false;
        }
        Double dLatitude = GpsDataConvertUtil.toDoubleDegree(latitude, isPositive);
        //设备id
        String deviceID = thirdGpsRecord.getDeviceID();
        //设备名称
        String deviceName = thirdGpsRecord.getDeviceName();
        //速度
        Integer speed = thirdGpsRecord.getSpeed();
        if (Objects.nonNull(speed)) {
            //统一数据格式(km/h)
            speed = speed / 100000;
        }
        Integer direction = thirdGpsRecord.getDirection();
        if (Objects.nonNull(direction)) {
            //统一数据格式(0-360)
            direction = direction / 100;
        }
        //数据封装
        GpsRecord gpsRecord = new GpsRecord();
        gpsRecord.setDeviceNo(deviceID);
        gpsRecord.setDeviceName(deviceName);
        gpsRecord.setGpsTime(time);
        gpsRecord.setLongitude(dLongitude);
        gpsRecord.setLatitude(dLatitude);
        gpsRecord.setSpeed(speed);
        gpsRecord.setDirection(direction);
        //缓存redis
        try {
            redisUtil.set(RedisKey.GPS_INFO.concat(deviceID), gpsRecord, EXPIRE_TIME_20);
        } catch (Exception e) {
            log.error("{}-{}-{} gps数据缓存异常 {}", time, deviceID, deviceName, e);
        }
        //存储es
    }
}
