package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.common.resp.RestMessage;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.EventDis.CommEventLog;
import com.drore.tdp.common.constant.AlarmType;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.HttpClientUtil;
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
 * @Created 2019/2/22  16:08.
 */
@Slf4j
@Component
public class SosServiceImpl {
    @Value("${tdp.control.url}")
    private String alarmUrl;
    @Autowired
    private QueryUtil queryUtil;

    public void sos(CommEventLog commEventLog) {
        int eventState = commEventLog.getEventState();
        if (Hk8700Constant.SOS_STATUS != eventState) {
            return;
        }
        String sourceIdx = commEventLog.getSourceIdx();
        CameraDevice cameraDevice = queryUtil.getCameraDeviceByIndexCode(sourceIdx);
        if (Objects.isNull(cameraDevice)) {
            log.error("sos报警柱设备监控点id {} 未匹配到sos报警柱设备信息", sourceIdx);
            return;
        }
        AlarmInfo alarmInfo = new AlarmInfo();
        String triggerDevice = cameraDevice.getDeviceIp() + "_" + cameraDevice.getChannelNo();
        alarmInfo.setAlarmType(AlarmType.ALARM_TYPE_SOS);
        alarmInfo.setTriggerDevice(triggerDevice);
        RestMessage restMessage = HttpClientUtil.httpPost(alarmUrl, JSON.parseObject(JSON.toJSONString(alarmInfo)), RestMessage.class);
        String time = DateTimeUtil.nowDateTimeString();
        if (Objects.nonNull(restMessage) && restMessage.isSuccess()) {
            log.info("{[]}-{[]}-{[]} sos报警柱报警成功", time, cameraDevice.getDeviceName(), triggerDevice);
        } else {
            log.error("{[]}-{[]}-{[]} sos报警柱报警失败 {}", time, cameraDevice.getDeviceName(), triggerDevice, restMessage.getMessage());
        }
    }
}
