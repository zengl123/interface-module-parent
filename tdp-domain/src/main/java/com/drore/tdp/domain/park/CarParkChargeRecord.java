package com.drore.tdp.domain.park;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  17:54.
 */
@Data
public class CarParkChargeRecord {
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
     * 车牌号码
     */
    @JSONField(name = "plate_number")
    @SerializedName(value = "plate_number")
    private String plateNumber;
    /**
     * 总收费金额
     */
    @JSONField(name = "total_cost")
    @SerializedName(value = "total_cost")
    private BigDecimal totalCost;
    /**
     * 应收金额
     */
    @JSONField(name = "real_cost")
    @SerializedName(value = "real_cost")
    private BigDecimal realCost;
    /**
     * 实收金额
     */
    private BigDecimal cost;
    /**
     * 收费规则名称
     */
    @JSONField(name = "charge_rule_name")
    @SerializedName(value = "charge_rule_name")
    private String chargeRuleName;
    /**
     * 异常放行规则名称
     */
    @JSONField(name = "exception_rule_name")
    @SerializedName(value = "exception_rule_name")
    private String exceptionRuleName;
    /**
     * 减免类型
     */
    @JSONField(name = "reduce_type")
    @SerializedName(value = "reduce_type")
    private Integer reduceType;
    /**
     * 收费类型
     */
    @JSONField(name = "charge_type")
    @SerializedName(value = "charge_type")
    private Integer chargeType;
    /**
     * 入场时间
     */
    @JSONField(name = "entry_time")
    @SerializedName(value = "entry_time")
    private String entryTime;
    /**
     * 出场时间
     */
    @JSONField(name = "export_time")
    @SerializedName(value = "export_time")
    private String exportTime;
    /**
     * 停车时长
     */
    @JSONField(name = "stop_time")
    @SerializedName(value = "stop_time")
    private Double stopTime;

    /**
     * 操作员
     */
    private String operator;
}
