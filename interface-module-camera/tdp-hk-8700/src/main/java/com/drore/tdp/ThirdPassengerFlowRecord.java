package com.drore.tdp;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/19  15:10.
 */
@Data
public class ThirdPassengerFlowRecord {
    private Integer passengersIn;
    private Integer passengersOut;
    private String footfallStartTime;
    private String footfallEndTime;
}
