package com.drore.tdp;

import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import com.drore.tdp.service.impl.CameraServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  14:30.
 */
public class Hk8700Test {

    private String host = "http://192.168.10.21";

    private String appKey = "fac30958";

    private String secret = "40b4809c4f204f80a9d81a288e5a4d8c";

    @Test
    public void test() {
        String userUuid = new CameraServiceImpl().getDefaultUserUuid(host, appKey, secret);
        String getPlatSubsystemCode = new CameraServiceImpl().getPlatSubsystemCode(host, appKey, secret, userUuid);
        String defaultUnit = new CameraServiceImpl().getDefaultUnit(host, appKey, secret, userUuid, getPlatSubsystemCode);
        //获取分组列表
        List<CameraGroup> cameraGroups = new CameraServiceImpl().getCameraGroups(host, appKey, secret, userUuid, defaultUnit, Hk8700Constant.ALL_CHILD);
        String netZones =  new CameraServiceImpl().getNetZones(host, appKey, secret, userUuid);
        List<CameraDevice> cameraDevice =  new CameraServiceImpl().getCameraDevice(host, appKey, secret, userUuid, netZones);
    }
}
