package com.drore.tdp.domain.park;

import com.alibaba.fastjson.annotation.JSONField;
import com.drore.tdp.domain.BaseModel;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:停车场设备信息
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:24.
 */
@Data
public class CarParkDevice extends BaseModel {
    /**
     * 设备编号
     */
    @JSONField(name = "device_no")
    @SerializedName(value = "device_no")
    private String deviceNo;
    /**
     * 设备名称
     */
    @JSONField(name = "device_name")
    @SerializedName(value = "device_name")
    private String deviceName;
    /**
     * 总车位
     */
    @JSONField(name = "total_number")
    @SerializedName("total_number")
    private Integer totalNumber;
    /**
     * 剩余车位
     */
    @JSONField(name = "remainder_number")
    @SerializedName("remainder_number")
    private Integer remainderNumber;
    /**
     * 已使用车位
     */
    @JSONField(name = "used_number")
    @SerializedName(value = "used_number")
    private Integer usedNumber;
}
