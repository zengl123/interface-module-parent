package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  9:36.
 */
@Data
public class ThirdCameraDevice {
    /**
     * 区域UUID
     */
    private String regionUuid;
    /**
     * 编码设备UUID
     */
    private String encoderUuid;
    /**
     * 监控点UUID
     */
    private String cameraUuid;
    /**
     * 监控点名称
     */
    private String cameraName;
    /**
     * 监控点类型
     */
    private Integer cameraType;
    /**
     * 通道号
     */
    private Integer cameraChannelNum;
    /**
     * 在线状态(0-不在线,1-在线)
     */
    private Integer onLineStatus;
    /**
     * 预览参数
     */
    private String previewParam;
    /**
     * 回放参数
     */
    private String playBackParam;
}
