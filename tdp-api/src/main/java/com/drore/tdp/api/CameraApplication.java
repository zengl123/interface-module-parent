package com.drore.tdp.api;

import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.service.ICameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  14:27.
 */
@Api(tags = "监控模块接口说明")
@RestController
@RequestMapping(value = "/tdp/camera/")
public class CameraApplication {
    @Autowired
    private ICameraService service;

    /**
     * 监控数据同步(手动刷新)
     *
     * @return
     */
    @ApiOperation(value = "监控数据同步接口")
    @GetMapping(value = "syncCamera")
    public ResponseBase syncCamera() {
        return service.syncCamera();
    }

    /**
     * 实时获取平台监控设备信息
     *
     * @return
     */
    @ApiOperation(value = "获取平台监控设备信息")
    @GetMapping(value = "listCameraDeviceInfo")
    public ResponseBase listCameraDeviceInfo() {
        return service.listCameraDeviceInfo();
    }
}
