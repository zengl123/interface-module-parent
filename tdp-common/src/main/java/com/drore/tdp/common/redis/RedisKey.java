package com.drore.tdp.common.redis;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  9:47.
 */
public interface RedisKey {
    Long EXPIRE_TIME_10 = 10L;
    Long EXPIRE_TIME_20 = 20L;
    Long EXPIRE_TIME_60 = 60L;
    Long EXPIRE_TIME_70 = 70L;
    Long EXPIRE_TIME_300 = 300L;
    /**
     * 实时监控信息
     */
    String CAMERA_INFO = "tdp_camera_info";
    /**
     * 实时gps信息记录
     */
    String GPS_INFO = "tdp_gps_";
    /**
     * 实时客流监控信息记录
     */
    String PASSENGER_FLOW_INFO = "tdp_passenger_";
}
