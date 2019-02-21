package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  15:11.
 */
@Data
public class ThirdGpsRecord {
    private String Time;
    private Integer Latitude;
    private Integer Longitude;
    private String DeviceID;
    private String DeviceName;
    private Integer Speed;
    private Integer Direction;
}
