package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.common.util.MD5Util;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.ThirdCameraDevice;
import com.drore.tdp.bo.ThirdCameraGroup;
import com.drore.tdp.bo.ThirdEncoderDevice;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.constant.Hk8700Constant;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import com.drore.tdp.service.CameraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  14:01.
 */
@Slf4j
@Service
public class CameraServiceImpl extends BaseApiService implements CameraService {
    @Value("tdp.camera.params.host")
    private String host;
    @Value("tdp.camera.params.appKey")
    private String appKey;
    @Value("tdp.camera.params.secret")
    private String secret;
    @Autowired
    private QueryUtil queryUtil;

    /**
     * 同步监控数据
     *
     * @return
     */
    @Override
    public ResponseBase syncCamera() {
        String defaultUserUuid = getDefaultUserUuid(host, appKey, secret);
        String platSubsystemCode = getPlatSubsystemCode(host, appKey, secret, defaultUserUuid);
        String defaultUnit = getDefaultUnit(host, appKey, secret, defaultUserUuid, platSubsystemCode);
        List<CameraGroup> cameraGroups = getCameraGroups(host, appKey, secret, defaultUserUuid, defaultUnit, Hk8700Constant.ALL_CHILD);
        ResponseBase responseBase = queryUtil.saveOrUpdateCameraGroup(cameraGroups);
        if (!responseBase.isStatus()) {
            return error("海康8700监控数据同步失败");
        }
        String netZones = getNetZones(host, appKey, secret, defaultUserUuid);
        List<CameraDevice> cameraDevice = getCameraDevice(host, appKey, secret, defaultUserUuid, netZones);
        responseBase = queryUtil.saveOrUpdateCameraDevice(cameraDevice);
        if (responseBase.isStatus()) {
            return success("海康8700监控数据同步成功");
        } else {
            return error("海康8700监控数据同步失败");
        }
    }

    /**
     * 根据区域UUID集获取区域信息
     *
     * @param
     */
    public List<CameraGroup> getCameraGroups(String host, String appKey, String secret, String defaultUuid, String unitUuid, Integer allChild) {
        String path = Hk8700Constant.GET_REGION_BY_UNIT_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        param.put("parentUuid", unitUuid);
        param.put("allChildren", allChild);
        param.put("pageNo", 1);
        param.put("pageSize", 400);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        List<CameraGroup> list;
        if (null != response) {
            JSONObject data = response.getJSONObject("data");
            List<ThirdCameraGroup> thirdCameraGroups = JSONArray.parseArray(JSON.toJSONString(data.getJSONArray("list")), ThirdCameraGroup.class);
            log.debug("[响应结果-根据区域UUID集获取区域信息] {}", thirdCameraGroups);
            list = thirdCameraGroups.stream().map(thirdCameraGroup -> {
                CameraGroup cameraGroup = new CameraGroup();
                cameraGroup.setGroupNo(thirdCameraGroup.getRegionUuid());
                cameraGroup.setGroupName(thirdCameraGroup.getName());
                cameraGroup.setParentNo(thirdCameraGroup.getParentUuid());
                cameraGroup.setLeaveNo(thirdCameraGroup.getParentNodeType());
                cameraGroup.setSeries("HK8700");
                return cameraGroup;
            }).collect(Collectors.toList());
        } else {
            list = null;
        }
        return list;
    }

    /**
     * 获取监控点设备信息
     *
     * @return
     */
    public List<CameraDevice> getCameraDevice(String host, String appKey, String secret, String defaultUuid, String netZoneUuid) {
        List<ThirdCameraDevice> thirdCameraDevices = listCameraDevices(host, appKey, secret, defaultUuid, netZoneUuid);
        log.info("[监控点设备信息] {}", thirdCameraDevices.size());
        List<ThirdEncoderDevice> encoderDevices = listEncoderDevices(host, appKey, secret, defaultUuid);
        log.info("[监控点编码信息] {}", encoderDevices.size());
        List<CameraDevice> cameraDevices = new ArrayList<>();
        encoderDevices.stream().forEach(encoderDevice -> {
            String encoderUuid = encoderDevice.getEncoderUuid();
            String encoderIp = encoderDevice.getEncoderIp();
            Integer encoderPort = encoderDevice.getEncoderPort();
            thirdCameraDevices.stream().forEach(thirdCameraDevice -> {
                String encoderUuid1 = thirdCameraDevice.getEncoderUuid();
                CameraDevice cameraDevice = new CameraDevice();
                if (encoderUuid.equals(encoderUuid1)) {
                    cameraDevice.setGroupNo(thirdCameraDevice.getRegionUuid());
                    cameraDevice.setDeviceNo(thirdCameraDevice.getEncoderUuid());
                    cameraDevice.setDeviceName(thirdCameraDevice.getCameraName());
                    cameraDevice.setIndexCode(thirdCameraDevice.getCameraUuid());
                    cameraDevice.setDeviceIp(encoderIp);
                    cameraDevice.setDevicePort(encoderPort);
                    cameraDevice.setChannelNo(thirdCameraDevice.getCameraChannelNum());
                    cameraDevice.setStatus(thirdCameraDevice.getOnLineStatus());
                    cameraDevice.setPreviewParameter(thirdCameraDevice.getPreviewParam());
                    cameraDevice.setPlaybackParameter(thirdCameraDevice.getPlayBackParam());
                    cameraDevice.setDeviceTypeNo(thirdCameraDevice.getCameraType());
                    cameraDevices.add(cameraDevice);
                }
            });
        });
        log.debug("cameraDevices:{}", cameraDevices.size());
        return cameraDevices;
    }

    /**
     * 分页获取编码设备
     *
     * @param host
     * @param appKey
     * @param secret
     * @param defaultUuid
     * @return
     */
    public List<ThirdEncoderDevice> listEncoderDevices(String host, String appKey, String secret, String defaultUuid) {
        String path = Hk8700Constant.GET_ENCODER_DEVICES_EX;
        List<JSONObject> list = listDevice(host, appKey, secret, defaultUuid, path);
        return list.stream().map(object -> JSON.toJavaObject(object, ThirdEncoderDevice.class)).collect(Collectors.toList());
    }

    /**
     * 分页获取监控点
     *
     * @param host
     * @param appKey
     * @param secret
     * @param defaultUuid
     * @param netZoneUuid
     * @return
     */
    public List<ThirdCameraDevice> listCameraDevices(String host, String appKey, String secret, String defaultUuid, String netZoneUuid) {
        String path = Hk8700Constant.GET_CAMERA;
        List<JSONObject> list = listDevice(host, appKey, secret, defaultUuid, path);
        List<ThirdCameraDevice> thirdCameraDevices = list.stream().map(object -> JSON.toJavaObject(object, ThirdCameraDevice.class)).collect(Collectors.toList());
        return thirdCameraDevices.stream().map(thirdCameraDevice -> {
            String cameraUuid = thirdCameraDevice.getCameraUuid();
            String previewParamByPlanUuid = getPreviewParamByPlanUuid(host, appKey, secret, defaultUuid, cameraUuid, netZoneUuid);
            thirdCameraDevice.setPreviewParam(previewParamByPlanUuid);
            JSONObject recordPlanByCameraUuid = getRecordPlanByCameraUuid(host, appKey, secret, defaultUuid, cameraUuid, netZoneUuid);
            String recordPlanUuid = recordPlanByCameraUuid.getString("recordPlanUuid");
            Integer planType = recordPlanByCameraUuid.getInteger("planType");
            String playBackParamByPlanUuid = getPlayBackParamByPlanUuid(host, appKey, secret, defaultUuid, planType, recordPlanUuid, netZoneUuid);
            thirdCameraDevice.setPlayBackParam(playBackParamByPlanUuid);
            return thirdCameraDevice;
        }).collect(Collectors.toList());
    }

    private List<JSONObject> listDevice(String host, String appKey, String secret, String defaultUuid, String path) {
        JSONObject param = new JSONObject();
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        List<JSONObject> listResult = new ArrayList();
        param.put("pageSize", Hk8700Constant.PAGE_SIZE);
        Integer pageNo = Hk8700Constant.PAGE_NO;
        do {
            param.put("time", System.currentTimeMillis());
            param.put("pageNo", pageNo);
            String url = postBuildToken(host, path, param, secret);
            JSONObject response = HttpClientUtil.httpPost(url, param);
            if (null == response) {
                continue;
            }
            JSONObject data = response.getJSONObject("data");
            Integer total = data.getInteger("total");
            Integer pageSize = data.getInteger("pageSize");
            List list = data.getJSONArray("list");
            if (pageNo == 1) {
                pageNo = total / pageSize + 1;
            } else {
                pageNo--;
            }
            listResult.addAll(list);
        } while (pageNo > 1);
        return listResult;
    }

    /**
     * 获取默认用户UUID
     *
     * @return
     */

    public String getDefaultUserUuid(String host, String appKey, String secret) {
        String path = Hk8700Constant.GET_DEFAULT_USER_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String defaultUuid;
        if (null != response) {
            defaultUuid = response.getString("data");
        } else {
            defaultUuid = "";
        }
        log.info("[响应结果-默认用户id] {}", defaultUuid);
        return defaultUuid;
    }

    /**
     * 获取子系统列表
     *
     * @return
     */
    public String getPlatSubsystemCode(String host, String appKey, String secret, String defaultUuid) {
        String path = Hk8700Constant.GET_PLAT_SUB_SYSTEM;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        List list;
        String cameraSubSystemCode = "";
        if (null != response) {
            list = Arrays.asList(response.getJSONArray("data"));
            List<JSONObject> data = (List<JSONObject>) list;
            for (int i = 0; i < data.size(); i++) {
                if (Hk8700Constant.SYSTEM_NAME.equals(data.get(i).get("subSystemName"))) {
                    cameraSubSystemCode = data.get(i).getString("subSystemUuid");
                }
            }
        }
        log.info("[响应结果-视频cameraSubSystemCode] {}", cameraSubSystemCode);
        return cameraSubSystemCode;
    }

    /**
     * 获取默认控制中心
     *
     * @return
     */
    public String getDefaultUnit(String host, String appKey, String secret, String defaultUuid, String subSystemCode) {
        String path = Hk8700Constant.GET_DEFAULT_UNIT;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        param.put("subSystemCode", subSystemCode);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String unitUuid;
        if (null != response) {
            JSONObject data = response.getJSONObject("data");
            unitUuid = data.getString("unitUuid");
        } else {
            unitUuid = "";
        }
        log.info("[响应结果-默认控制中心] {}", unitUuid);
        return unitUuid;
    }

    /**
     * 获取所有网域
     *
     * @param opUserUuid
     * @return
     */
    public String getNetZones(String host, String appKey, String secret, String opUserUuid) {
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", opUserUuid);
        param.put("pageNo", 1);
        param.put("pageSize", 400);
        String path = Hk8700Constant.GET_NET_ZONES;
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String netZoneUuid;
        if (null != response) {
            JSONArray data = response.getJSONArray("data");
            //网域这里有两种结果0表示内网,1表示外网
            netZoneUuid = data.getJSONObject(0).getString("netZoneUuid");
        } else {
            netZoneUuid = "";
        }
        log.info("[响应结果-内网网域] {}", netZoneUuid);
        return netZoneUuid;
    }

    /**
     * 获取预览参数
     *
     * @param opUserUuid
     * @param netZoneUuid
     * @return
     */
    public String getPreviewParamByPlanUuid(String host, String appKey, String secret, String opUserUuid, String cameraUuid, String netZoneUuid) {
        String path = Hk8700Constant.GET_PREVIEW_PARAM_BY_CAMERA_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", opUserUuid);
        param.put("cameraUuid", cameraUuid);
        param.put("netZoneUuid", netZoneUuid);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String result;
        if (null != response) {
            result = response.getString("data");
        } else {
            result = "";
        }
        return result;
    }

    /**
     * 根据监控点UUID集和网域UUID分页获取录像计划
     *
     * @param cameraUuid
     * @return
     */
    public JSONObject getRecordPlanByCameraUuid(String host, String appKey, String secret, String defaultUuid, String cameraUuid, String netZoneUuid) {
        String path = Hk8700Constant.GET_RECORD_PLAN_BY_CAMERA_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", defaultUuid);
        param.put("cameraUuids", cameraUuid);
        param.put("netZoneUuid", netZoneUuid);
        param.put("pageNo", 1);
        param.put("pageSize", 400);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        if (null != response) {
            JSONObject data = response.getJSONObject("data");
            if (data != null) {
                //获取录像计划类型 一共有三种情况：1、设备存储，2、CVR存储，3、CVM存储
                return data.getJSONArray("list").getJSONObject(1);
            } else {
                log.error("[获取录像计划类型失败]");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 根据录像计划UUID和网域UUID获取回放参数
     *
     * @param opUserUuid
     * @param planType
     * @param recordPlanUuid
     * @param netZoneUuid
     * @return
     */

    public String getPlayBackParamByPlanUuid(String host, String appKey, String secret, String opUserUuid, Integer planType, String recordPlanUuid, String netZoneUuid) {
        String path = Hk8700Constant.GET_PLAY_BACK_PARAM_BY_PLAN_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        param.put("opUserUuid", opUserUuid);
        param.put("planType", planType);
        param.put("recordPlanUuid", recordPlanUuid);
        param.put("netZoneUuid", netZoneUuid);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String result;
        if (null != response) {
            result = response.getString("data");
        } else {
            result = "";
        }
        return result;
    }

    public static final String postBuildToken(String host, String path, JSONObject param, String secret) {
        StringBuilder url = new StringBuilder();
        url.append(host).append(path);
        String jsonString = JSONObject.toJSONString(param);
        StringBuilder builder = new StringBuilder();
        String md5String = builder.append(path).append(jsonString).append(secret).toString();
        String token = MD5Util.getMD5Str(md5String).toUpperCase();
        url.append("?token=").append(token);
        return url.toString();
    }
}