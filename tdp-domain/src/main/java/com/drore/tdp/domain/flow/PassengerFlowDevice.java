package com.drore.tdp.domain.flow;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:监控客流设备信息
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/19  14:01.
 */
@Data
public class PassengerFlowDevice {
    /**
     * 监控客流设备编号
     */
    @JSONField(name = "device_no")
    @SerializedName(value = "device_no")
    private String deviceNo;
    /**
     * 监控客流设备名称
     */
    @JSONField(name = "device_name")
    @SerializedName(value = "device_name")
    private String deviceName;
    /**
     * 监控客流上级组织编码
     */
    @JSONField(name = "parent_no")
    @SerializedName(value = "parent_no")
    private String parentNo;
    /**
     * 监控客流上级组织名称
     */
    @JSONField(name = "parent_name")
    @SerializedName(value = "parent_name")
    private String parentName;
    /**
     * 峰值
     */
    private Integer threshold;
    /**
     * 设备ip
     */
    @JSONField(name = "device_ip")
    @SerializedName(value = "device_ip")
    private String deviceIp;
}
