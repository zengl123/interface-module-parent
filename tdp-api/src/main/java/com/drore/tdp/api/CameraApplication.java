package com.drore.tdp.api;

import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.service.CameraService;
import com.drore.tdp.service.ICameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "更新监控数据")
    @GetMapping(value = "update")
    public ResponseBase update() {
        return service.update();
    }


    @ApiOperation(value = "获取平台监控设备信息")
    @GetMapping(value = "getCameraDeviceInfo")
    public ResponseBase getCameraDeviceInfo() {
        return service.getCameraDeviceInfo();
    }

    @ApiOperation(value = "根据指定条件查询监控列表")
    @RequestMapping(value = "getCameraGroupByRequestBody", method = RequestMethod.POST)
    public ResponseBase getCameraGroupByRequestBody(@RequestBody(required = false) JSONObject requestBody) {
        return service.getCameraGroupByRequestBody(requestBody);
    }

    @ApiOperation(value = "根据指定条件查询监控列表")
    @RequestMapping(value = "getCameraDeviceByRequestBody", method = RequestMethod.POST)
    public ResponseBase getCameraDeviceByRequestBody(@RequestBody(required = false) JSONObject requestBody) {
        return service.getCameraDeviceByRequestBody(requestBody);
    }
}
