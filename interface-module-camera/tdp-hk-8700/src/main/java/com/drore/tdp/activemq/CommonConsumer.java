package com.drore.tdp.activemq;

import com.drore.tdp.bo.EventDis;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.service.impl.*;
import com.drore.tdp.utils.Hk8700Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import java.util.Objects;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  15:21.
 */
@Slf4j
@Component
public class CommonConsumer {
    @Autowired
    private PersonnelDensityServiceImpl personnelDensityServiceImpl;
    @Autowired
    private DangerousAreaServiceImpl dangerousAreaServiceImpl;
    @Autowired
    private PassengerFlowServiceImpl passengerFlowServiceImpl;
    @Autowired
    private SosServiceImpl sosServiceImpl;
    @Autowired
    private FireServiceImpl fireServiceImpl;

    @JmsListener(destination = "${tdp.hk.common-destination}")
    public void getMessage(BytesMessage message) {
        EventDis.CommEventLog commEventLog = Hk8700Util.getCommEventLog(message);
        if (Objects.isNull(commEventLog)) {
            return;
        }
        int eventType = commEventLog.getEventType();
        switch (eventType) {
            case Hk8700Constant.EVENT_TYPE_BEHAVIOURAL:
                personnelDensityServiceImpl.personnelDensity(commEventLog);
                break;
            case Hk8700Constant.EVENT_TYPE_DANGEROUS:
                dangerousAreaServiceImpl.dangerous(commEventLog);
                break;
            case Hk8700Constant.EVENT_TYPE_PASSENGER_FLOW:
                passengerFlowServiceImpl.passengerFlow(commEventLog);
                break;
            case Hk8700Constant.EVENT_TYPE_SOS:
                sosServiceImpl.sos(commEventLog);
                break;
            case Hk8700Constant.EVENT_TYPE_FIRE:
                fireServiceImpl.fire(commEventLog);
                break;
            default:
                log.info("其它事件码 {}", eventType);
                break;
        }
    }
}
