package com.drore.tdp.schedule;


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
    private CarParkServiceImpl parkServiceimpl;

    @Autowired
    private PassengerFlowServiceImpl flowServiceImpl;

    //@Scheduled(cron = "${tdp.schedule.sync-car-park-device}")
    public void syncCarParkDevice() {
        parkServiceimpl.syncCarParkDevice();
    }

    //@Scheduled(cron = "${tdp.schedule.sync-car-park-record}")
    public void syncCarParkRecord() {
        parkServiceimpl.syncRecord();
    }

    @Scheduled(cron = "${tdp.schedule.sync-passenger-flow-record}")
    public void syncPassengerFlowRecord() {
        flowServiceImpl.syncPassengerFlowRecord();
    }

}
