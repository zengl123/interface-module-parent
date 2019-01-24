package com.drore.tdp.service;

import com.drore.tdp.common.base.ResponseBase;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  14:24.
 */
public interface ICameraService {
    /**
     * 同步监控数据
     *
     * @return
     */
    ResponseBase syncCamera();

    /**
     * 实时获取平台监控设备信息
     *
     * @return
     */
    ResponseBase listCameraDeviceInfo();
}
