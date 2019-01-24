package com.drore.tdp.service.impl;

import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.service.ICameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  14:26.
 */
@Service
public class CameraServiceImpl extends BaseApiService implements ICameraService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 同步监控数据
     *
     * @return
     */
    @Override
    public ResponseBase syncCamera() {
        redisUtil.set("test","123");
        String test = redisUtil.get("test");
        return success(test);
    }

    @Override
    public ResponseBase listCameraDeviceInfo() {
        return success();
    }
}
