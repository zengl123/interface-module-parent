package com.drore.tdp.schedule;


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

    @Scheduled(cron = "${}")
    public void syncCarParkDevice() {

    }
}
