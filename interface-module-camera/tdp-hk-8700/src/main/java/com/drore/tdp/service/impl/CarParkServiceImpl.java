package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.ThirdCarPark;
import com.drore.tdp.bo.ThirdCarParkRecord;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.constant.SyncTimeCode;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.park.CarParkParkDevice;
import com.drore.tdp.domain.park.CarParkRecord;
import com.drore.tdp.utils.Hk8700Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.drore.tdp.utils.Hk8700Util.getDefaultUserUuid;
import static com.drore.tdp.utils.Hk8700Util.postBuildToken;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  11:33.
 */
@Slf4j
@Service
public class CarParkServiceImpl extends BaseApiService {
    @Value("tdp.camera.params.host")
    private String host;
    @Value("tdp.camera.params.appKey")
    private String appKey;
    @Value("tdp.camera.params.secret")
    private String secret;
    @Value("tdp.camera.sync.car-park-record")
    private String carParkRecord;
    @Autowired
    private QueryUtil queryUtil;

    /**
     * 同步停车场设备信息
     *
     * @return
     */
    public ResponseBase syncCarParkDevice() {
        Long time = System.currentTimeMillis();
        String userUuid = getDefaultUserUuid(host, appKey, secret);
        try {
            List<CarParkParkDevice> carParkParkDevices = listParkGroup(host, appKey, secret, userUuid);
            log.debug("[停车场信息] {}", carParkParkDevices);
            if (null == carParkParkDevices) {
                return error("同步停车场设备信息失败");
            }
            ResponseBase responseBase = queryUtil.saveOrUpdateCarParkDevice(carParkParkDevices);
            if (!responseBase.isStatus()) {
                return error("同步停车场设备信息失败");
            }
        } catch (Exception e) {
            log.error("同步停车场设备信息异常:{}", e);
            return error("同步停车场设备信息失败");
        }
        time = System.currentTimeMillis() - time;
        log.info("同步停车场设备信息成功,耗时:{}毫秒", time);
        return success("同步停车场设备信息成功");
    }

    /**
     * 同步停车场历史记录
     *
     * @return
     */
    public ResponseBase syncRecord() {
        String userUuid = Hk8700Util.getDefaultUserUuid(host, appKey, secret);
        String beginTime = queryUtil.getSyncTime(SyncTimeCode.CAR_PARK_RECORD);
        return getRecord(host, appKey, secret, userUuid, beginTime);
    }

    public ResponseBase getRecord(String host, String appKey, String secret, String defaultUuid, String beginTime) {
        String path = Hk8700Constant.GET_VEHICLE_RECORDS;
        String endTime;
        String nowTime = DateTimeUtil.nowDateTimeString();
        Integer pageNo = Hk8700Constant.PAGE_NO;
        Integer pageSize = Hk8700Constant.PAGE_SIZE;
        //发生异常|错误退出标识
        boolean flag = true;
        ResponseBase responseBase;
        try {
            do {
                if (StringUtils.isEmpty(beginTime)) {
                    beginTime = carParkRecord;
                } else {
                    beginTime = DateTimeUtil.stringPlusSeconds(beginTime, 1);
                }
                endTime = DateTimeUtil.stringPlusDays(beginTime, 1);
                if (endTime.compareTo(nowTime) > 0) {
                    endTime = nowTime;
                }
                JSONObject param = new JSONObject();
                param.put("appkey", appKey);
                param.put("opUserUuid", defaultUuid);
                param.put("pageSize", pageSize);
                param.put("startTime", DateTimeUtil.stringToTimestamp(beginTime));
                param.put("endTime", DateTimeUtil.stringToTimestamp(endTime));
                do {
                    param.put("time", System.currentTimeMillis());
                    param.put("pageNo", pageNo);
                    String token = postBuildToken(host, path, param, secret);
                    JSONObject response = HttpClientUtil.httpPost(token, param);
                    if (null == response) {
                        flag = false;
                        break;
                    }
                    JSONObject data = response.getJSONObject("data");
                    Integer total = data.getInteger("total");
                    pageSize = data.getInteger("pageSize");
                    if (pageNo == 1) {
                        pageNo = total / pageSize + 1;
                    } else {
                        pageNo--;
                    }
                    JSONArray jsonArray = data.getJSONArray("list");
                    List<ThirdCarParkRecord> thirdCarParkRecords = JSONArray.parseArray(JSON.toJSONString(jsonArray), ThirdCarParkRecord.class);
                    List<CarParkRecord> carParkRecords = thirdCarParkRecords.stream().map(thirdCarParkRecord -> {
                        CarParkRecord carParkRecord = new CarParkRecord();
                        carParkRecord.setDeviceNo(thirdCarParkRecord.getParkUuid());
                        carParkRecord.setDeviceName(thirdCarParkRecord.getEntranceName());
                        carParkRecord.setPlateNumber(thirdCarParkRecord.getPlateNo());
                        Integer carOut = thirdCarParkRecord.getCarOut();
                        if (Hk8700Constant.CAR_IN.equals(carOut)) {
                            carParkRecord.setEntryTime(thirdCarParkRecord.getCrossTime());
                        } else if (Hk8700Constant.CAR_OUT.equals(carOut)) {
                            carParkRecord.setExportTime(thirdCarParkRecord.getCrossTime());
                        }
                        carParkRecord.setVehicleType(thirdCarParkRecord.getVehicleType());
                        carParkRecord.setPlatePicUrl(thirdCarParkRecord.getPlatePicUrl());
                        carParkRecord.setVehiclePicUrl(thirdCarParkRecord.getVehiclePicUrl());
                        return carParkRecord;
                    }).collect(Collectors.toList());
                    queryUtil.saveCarParkRecord(carParkRecords);
                } while (pageNo > 1);
                if (flag) {
                    beginTime = endTime;
                } else {
                    break;
                }
            } while (endTime.compareTo(nowTime) < 0);

            return success();
        } catch (Exception e) {
            log.error("[同步停车场历史记录异常] {}", e);
            return error();
        }
    }

    /**
     * 停车场信息
     *
     * @return
     */
    public List<CarParkParkDevice> listParkGroup(String host, String appKey, String secret, String defaultUuid) {
        String path = Hk8700Constant.GET_PARKING_INFO;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        if (null == response) {
            return null;
        }
        JSONArray data = response.getJSONArray("data");
        List<ThirdCarPark> thirdCarParks = JSONObject.parseArray(data.toJSONString(), ThirdCarPark.class);
        return thirdCarParks.stream().map(thirdCarPark -> {
            CarParkParkDevice carParkParkDevice = new CarParkParkDevice();
            carParkParkDevice.setDeviceNo(thirdCarPark.getParkUuid());
            carParkParkDevice.setDeviceName(thirdCarPark.getParkName());
            Integer totalPlot = thirdCarPark.getTotalPlot();
            Integer leftPlot = thirdCarPark.getLeftPlot();
            Integer used = totalPlot - leftPlot < 0 ? 0 : totalPlot - leftPlot;
            carParkParkDevice.setTotalNumber(totalPlot);
            carParkParkDevice.setRemainderNumber(leftPlot);
            carParkParkDevice.setUsedNumber(used);
            return carParkParkDevice;
        }).collect(Collectors.toList());
    }
}
