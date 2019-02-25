package com.drore.tdp.domain.alarm;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 描述:警报实体类
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  9:59.
 */
@Data
public class AlarmInfo {
    @JSONField(name = "alarm_type")
    private String alarmType;
    @JSONField(name = "trigger_device")
    private String triggerDevice;
}
