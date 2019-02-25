package com.drore.tdp.domain.flow;

import com.alibaba.fastjson.annotation.JSONField;
import com.drore.tdp.domain.BaseModel;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:人员密度
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  14:15.
 */
@Data
public class PersonnelDensityRecord {
    @JSONField(name = "device_no")
    @SerializedName(value = "device_no")
    private String deviceNo;
    @JSONField(name = "device_name")
    @SerializedName(value = "device_name")
    private String deviceName;
    @JSONField(name = "device_ip")
    @SerializedName(value = "device_ip")
    private String deviceIp;
    @JSONField(name = "count_time")
    @SerializedName(value = "count_time")
    private String countTime;
    private Integer number;
}
