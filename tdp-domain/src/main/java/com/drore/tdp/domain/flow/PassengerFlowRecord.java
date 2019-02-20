package com.drore.tdp.domain.flow;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/19  14:08.
 */
@Data
public class PassengerFlowRecord {
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
     * 数据实时上传客进入流人数
     */
    @JSONField(name = "enter_number")
    @SerializedName(value = "enter_number")
    private Integer enterNumber;
    /**
     * 数据实时上传客流离开人数
     */
    @JSONField(name = "leave_number")
    @SerializedName(value = "leave_number")
    private Integer leaveNumber;
}
