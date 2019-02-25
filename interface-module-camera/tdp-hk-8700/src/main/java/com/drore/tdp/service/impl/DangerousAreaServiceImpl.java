package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.common.resp.RestMessage;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.EventDis.CommEventLog;
import com.drore.tdp.common.constant.AlarmType;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.alarm.AlarmInfo;
import com.drore.tdp.domain.camera.CameraDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  15:30.
 */
@Slf4j
@Component
public class DangerousAreaServiceImpl {
    @Autowired
    private QueryUtil queryUtil;
    @Value("${tdp.control.url}")
    private String alarmUrl;

    public void dangerous(CommEventLog commEventLog) {
        int eventState = commEventLog.getEventState();
        if (Hk8700Constant.DANGEROUS_STATUS != eventState) {
            return;
        }
        //危险区域设备id
        String sourceIdx = commEventLog.getSourceIdx();
        //因为gis地图打点信息是设备IP地址所以我们要根据设备id去监控设备信息里关联设备IP地址
        CameraDevice cameraDevice = queryUtil.getCameraDeviceByIndexCode(sourceIdx);
        if (Objects.isNull(cameraDevice)) {
            log.error("危险区域设备监控点id {} 未匹配到监控设备信息", sourceIdx);
            return;
        }
        JSONObject jsonObject = XmlUtil.xml2json(commEventLog.getExtInfo().toStringUtf8());
        String channel = jsonObject.getJSONObject("ExtEventInfo").getString("DevInfoIvmsChannelEx");
        String triggerDevice = cameraDevice.getDeviceIp() + "_" + channel;
        //封装报警参数
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setAlarmType(AlarmType.ALARM_TYPE_DANGEROUS);
        alarmInfo.setTriggerDevice(triggerDevice);
        RestMessage restMessage = HttpClientUtil.httpPost(alarmUrl, JSON.parseObject(JSON.toJSONString(alarmInfo)), RestMessage.class);
        String time = DateTimeUtil.nowDateTimeString();
        if (Objects.nonNull(restMessage) && restMessage.isSuccess()) {
            log.info("{[]}-{[]}-{[]} 危险区域报警成功", time, cameraDevice.getDeviceName(), triggerDevice);
        } else {
            log.error("{[]}-{[]}-{[]} 危险区域报警失败 {}", time, cameraDevice.getDeviceName(), triggerDevice, restMessage.getMessage());
        }
    }
}
