package com.drore.tdp.constant;

/**
 * @author ZENLIN
 */
public interface Hk8700Constant {
    Integer PAGE_NO = 1;
    Integer PAGE_SIZE = 400;
    /**
     * 车辆进出
     */
    Integer CAR_IN = 0;
    Integer CAR_OUT = 1;

    Integer SUCCESS_RESPONSE = 0;

    /*******************监控******************/
    /**
     * 获取默认用户UUID的接口地址
     */
    String GET_DEFAULT_USER_UUID = "/openapi/service/base/user/getDefaultUserUuid";
    /**
     * 获取子系统
     */
    String GET_PLAT_SUB_SYSTEM = "/openapi/service/base/res/getPlatSubsytem";
    /**
     * 获取默认控制中心
     */
    String GET_DEFAULT_UNIT = "/openapi/service/base/org/getDefaultUnit";
    /**
     * 根据中心UUID分页获取下级区域
     */
    String GET_REGION_BY_UNIT_UUID = "/openapi/service/base/org/getRegionsByUnitUuid";
    /**
     * 根据区域UUID集分页获取监控点
     */
    String GET_CAMERA_BY_REGION_UUID = "/openapi/service/vss/res/getCamerasByRegionUuids";
    /**
     * 分页获取监控点
     */
    String GET_CAMERA = "/openapi/service/vss/res/getCameras";
    /**
     * 获取所有网域
     */
    String GET_NET_ZONES = "/openapi/service/base/netZone/getNetZones";
    /**
     * 根据监控点UUID集和网域UUID分页获取录像计划
     */
    String GET_RECORD_PLAN_BY_CAMERA_UUID = "/openapi/service/vss/playback/getRecordPlansByCameraUuids";
    /**
     * 根据录像计划UUID和网域UUID获取回放参数
     */
    String GET_PLAY_BACK_PARAM_BY_PLAN_UUID = "/openapi/service/vss/playback/getPlaybackParamByPlanUuid";
    /**
     * 根据监控点UUID和网域UUID获取预览参数
     */
    String GET_PREVIEW_PARAM_BY_CAMERA_UUID = "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
    String SYSTEM_NAME = "视频";
    /**
     * 分页获取编码设备
     */
    String GET_ENCODER_DEVICES_EX = "/openapi/service/vss/res/getEncoderDevicesEx";
    /**
     * 0-直接子中心,1-下级所有子区域
     */
    Integer ALL_CHILD = 0;

    /*******************停车场******************/
    /**
     * 获取所有停车场信息
     */
    String GET_PARKING_INFO = "/openapi/service/pms/res/getParkingInfos";
    /**
     * 获取所有过车记录
     */
    String GET_VEHICLE_RECORDS = "/openapi/service/pms/record/getVehicleRecords";
    /**
     * 获取停车场临时车缴费记录
     */
    String GET_TEMP_CAR_CHARGE_RECORDS = "/openapi/service/pms/record/getTempCarChargeRecords";

    /*******************监控客流******************/
    String GET_FOOTFALL_DATA = "/openapi/service/vss/footfall/getFootfallData";

    /*******************activeMq事件码*****************/
    /**
     * gps事件码
     */
    int EVENT_TYPE_GPS = 851969;
    /**
     * 监控客流事件码
     */
    int EVENT_TYPE_PASSENGER_FLOW = 131627;
    /**
     * 危险区域事件码
     */
    int EVENT_TYPE_DANGEROUS = 131585;
    /**
     * 行为分析(人员密度)事件码
     */
    int EVENT_TYPE_BEHAVIOURAL = 131655;
    /**
     * SOS事件码
     */
    int EVENT_TYPE_SOS = 327687;
    /**
     * 火警事件码
     */
    int EVENT_TYPE_FIRE = 196353;

    /**
     * <!—告警事件状态，0 瞬时，1 脉冲开始，2 脉冲持续，3 脉冲结束 -->
     */
    int DANGEROUS_STATUS = 0;
    /**
     * <!—告警事件状态，0 瞬时，1 脉冲开始，2 脉冲持续，3 脉冲结束 -->
     */
    int SOS_STATUS = 1;
    /**
     * 0：瞬时事件，保存；1：事件开始，保存；2：事件结束，更新结束时间；3：事件脉冲，客户端和服务器使用，CMS不用；4：事件更新，联动结果更新
     */
    int FIRE_STATUS = 0;
}
