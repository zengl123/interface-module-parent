package com.drore.tdp.domain.park;

import com.drore.tdp.domain.BaseModel;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/18  10:44.
 */
@Data
public class CarParkRecord extends BaseModel {
    private String deviceNo;
    private String deviceName;
    private String plateNumber;
    private String entryTime;
    private String exportTime;
    private Integer vehicleType;
    private String platePicUrl;
    private String vehiclePicUrl;
}
