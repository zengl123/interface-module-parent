package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  11:49.
 */
@Data
public class ThirdCarPark {
    private String parkUuid;
    private String parkName;
    private Integer totalPlot;
    private Integer leftPlot;
}
