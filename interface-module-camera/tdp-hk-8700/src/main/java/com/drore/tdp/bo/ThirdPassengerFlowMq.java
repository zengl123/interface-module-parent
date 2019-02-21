package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  17:23.
 */
@Data
public class ThirdPassengerFlowMq {
    /**
     * 统计开始时间
     */
    private String StartTime;
    /**
     * 统计结束时间
     */
    private String EndTime;
    /**
     * 实时进园人数
     */
    private Integer EnterNum;
    /**
     * 实时出园人数
     */
    private Integer LeaveNum;
    /**
     * 当天累计进园人数
     */
    private Integer FrmIn;
    /**
     * 当天累计出园人数
     */
    private Integer FrmOut;
}
