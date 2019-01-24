package com.drore.tdp.camera.schedule;

import com.drore.tdp.camera.service.ICameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  13:54.
 */
@Component
@EnableScheduling
public class CameraSchedule {
    @Autowired
    private ICameraService service;

    @Scheduled(cron = "${tdp.camera.schedule.sync-camera}")
    public void syncCamera() {
        service.syncCamera();
    }
}
