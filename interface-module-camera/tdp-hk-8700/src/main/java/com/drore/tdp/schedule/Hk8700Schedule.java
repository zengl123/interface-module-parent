package com.drore.tdp.schedule;


import com.drore.tdp.service.impl.CameraServiceImpl;
import com.drore.tdp.service.impl.CarParkServiceImpl;
import com.drore.tdp.service.impl.PassengerFlowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/18  17:50.
 */
@Component
@EnableScheduling
public class Hk8700Schedule {
    @Autowired
    private CarParkServiceImpl parkServiceImpl;
    @Autowired
    private PassengerFlowServiceImpl flowServiceImpl;
    @Autowired
    private CameraServiceImpl cameraServiceImpl;

    //@Scheduled(cron = "${tdp.schedule.sync-car-park-device}")
    public void syncCarParkDevice() {
        parkServiceImpl.syncCarParkDevice();
    }

    //@Scheduled(cron = "${tdp.schedule.sync-car-park-record}")
    public void syncCarParkRecord() {
        parkServiceImpl.syncRecord();
    }

    @Scheduled(cron = "${tdp.schedule.sync-car-park-charge-record}")
    public void syncChargeRecord() {
        parkServiceImpl.syncChargeRecord();
    }


    //@Scheduled(cron = "${tdp.schedule.sync-passenger-flow-record}")
    public void syncPassengerFlowRecord() {
        flowServiceImpl.syncPassengerFlowRecord();
    }

    //@Scheduled(cron = "${tdp.schedule.sync-camera}")
    public void syncCamera() {
        cameraServiceImpl.syncCamera();
    }
}
