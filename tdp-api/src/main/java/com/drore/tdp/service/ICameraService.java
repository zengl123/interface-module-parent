package com.drore.tdp.service;

import com.alibaba.fastjson.JSONObject;
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
    ResponseBase update();

    /**
     * 实时获取平台监控设备信息
     *
     * @return
     */
    ResponseBase getCameraDeviceInfo();

    /**
     * 根据指定条件查询监控列表(默认查询所有)
     *
     * @param requestBody
     * @return
     */
    ResponseBase getCameraGroupByRequestBody(JSONObject requestBody);

    /**
     * 根据指定条件查询监控设备信息
     *
     * @param requestBody
     * @return
     */
    ResponseBase getCameraDeviceByRequestBody(JSONObject requestBody);
}
