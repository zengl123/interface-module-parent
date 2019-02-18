package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/18  15:02.
 */
@Data
public class ThirdCarParkRecord {
    private String recordUuid;
    private String parkUuid;
    private String entranceName;
    private String plateNo;
    private String crossTime;
    private String operator;
    private Integer carOut;
    private String platePicUrl;
    private String vehiclePicUrl;
    private Integer vehicleType;
    private String roadwayUuid;
    private String roadwayName;
    private Integer vehicleColor;
}
