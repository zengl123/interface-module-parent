package com.drore.tdp.domain.park;

import com.alibaba.fastjson.annotation.JSONField;
import com.drore.tdp.domain.BaseModel;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:过车记录信息
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/18  10:44.
 */
@Data
public class CarParkRecord extends BaseModel {
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
     * 车辆类型
     */
    @JSONField(name = "vehicle_type")
    @SerializedName(value = "vehicle_type")
    private Integer vehicleType;
    /**
     * 车牌图片地址
     */
    @JSONField(name = "plate_pic_url")
    @SerializedName(value = "plate_pic_url")
    private String platePicUrl;
    /**
     * 车辆图片地址
     */
    @JSONField(name = "vehicle_pic_url")
    @SerializedName(value = "vehicle_pic_url")
    private String vehiclePicUrl;
    /**
     * 车辆进出标识
     */
    private Integer status;
}
