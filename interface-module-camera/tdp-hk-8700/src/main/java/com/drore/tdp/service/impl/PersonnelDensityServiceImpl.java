package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.EventDis.CommEventLog;
import com.drore.tdp.common.redis.RedisKey;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.domain.flow.PersonnelDensityRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/22  15:25.
 */

@Component
public class PersonnelDensityServiceImpl {
    private Logger log = LoggerFactory.getLogger("personnelDensity");
    @Autowired
    private QueryUtil queryUtil;
    @Autowired
    private RedisUtil redisUtil;

    public void personnelDensity(CommEventLog commEventLog) {
        String sourceIdx = commEventLog.getSourceIdx();
        String sourceName = commEventLog.getSourceName();
        String startTime = commEventLog.getStartTime();
        String extInfo = commEventLog.getExtInfo().toStringUtf8();
        JSONObject jsonObject = XmlUtil.xmlToJson(extInfo);
        JSONObject jsonData = JSONObject.parseObject(jsonObject.getString("JsonData"));
        String ipV4 = jsonData.getString("ipV4");
        String channel = jsonData.getString("channel");
        JSONObject target = jsonData.getJSONArray("Target").getJSONObject(0);
        JSONObject targetInfo = JSONObject.parseObject(target.getString("TargetInfo"));
        Integer number = targetInfo.getInteger("framesPeopleCounting_number");
        PersonnelDensityRecord personnelDensityRecord = new PersonnelDensityRecord();
        personnelDensityRecord.setDeviceNo(sourceIdx);
        personnelDensityRecord.setDeviceName(sourceName);
        personnelDensityRecord.setCountTime(startTime);
        personnelDensityRecord.setNumber(number);
        String deviceIp = ipV4 + "_" + channel;
        personnelDensityRecord.setDeviceIp(deviceIp);
        log.debug("人员密度数据 {}", personnelDensityRecord);
        try {
            redisUtil.set(RedisKey.PERSONNEL_DENSITY + deviceIp, personnelDensityRecord, RedisKey.EXPIRE_TIME_60);
        } catch (Exception e) {
            log.error("人员密度数据缓存异常 上传时间 {} 设备IP {} 设备名称 {} {}", startTime, deviceIp, sourceName, e);
        }
        //存储
    }
}
