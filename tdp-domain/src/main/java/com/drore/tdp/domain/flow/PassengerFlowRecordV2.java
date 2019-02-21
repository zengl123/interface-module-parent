package com.drore.tdp.domain.flow;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  17:36.
 */
@Data
public class PassengerFlowRecordV2 {
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
     * 开始时间
     */
    @JSONField(name = "start_time")
    @SerializedName(value = "start_time")
    private String startTime;
    /**
     * 结束时间
     */
    @JSONField(name = "end_time")
    @SerializedName(value = "end_time")
    private String endTime;
    /**
     * 进入人数
     */
    @JSONField(name = "enter_num")
    @SerializedName(value = "enter_num")
    private Integer enterNum;
    /**
     * 离开人数
     */
    @JSONField(name = "leave_num")
    @SerializedName(value = "leave_num")
    private Integer leaveNum;
    /**
     * 在园人数
     */
    @JSONField(name = "total_in")
    @SerializedName(value = "total_in")
    private Integer totalIn;
    /**
     * 在园人数
     */
    @JSONField(name = "total_out")
    @SerializedName(value = "total_out")
    private Integer totalOut;
    /**
     * 设备ip
     */
    @JSONField(name = "device_ip")
    @SerializedName(value = "device_ip")
    private String deviceIp;
}
