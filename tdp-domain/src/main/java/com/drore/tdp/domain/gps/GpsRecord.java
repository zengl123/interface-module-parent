package com.drore.tdp.domain.gps;

import lombok.Data;

/**
 * 描述:gps数据实体类
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  15:19.
 */
@Data
public class GpsRecord {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 上传时间
     */
    private String gpsTime;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 速度(km/h)
     */
    private Integer speed;
    /**
     * 方向
     */
    private Integer direction;
    private transient String directionName;
    /**
     * 海拔(m)
     */
    private Integer elevation;
}
