package com.drore.tdp.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  17:46.
 */
@Data
public class ThirdCarParkChargeRecord {
    private String billUuid;
    private String parkUuid;
    private String parkName;
    private String plateNo;
    private BigDecimal totalCost;
    private BigDecimal realCost;
    private BigDecimal cost;
    private String chargeRuleName;
    private String exceptionRuleName;
    private Integer reductType;
    private Integer chargeType;
    private String enterTime;
    private String costTime;
    private String operator;
}
