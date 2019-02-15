package com.drore.tdp.service;

import com.drore.tdp.common.base.ResponseBase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:46.
 */
@RestController
@RequestMapping("/interface/tdp/camera/")
public interface CameraService {
    /**
     * 同步监控数据
     *
     * @return
     */
    @GetMapping(value = "syncCamera")
    ResponseBase syncCamera();
}
