package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.QueryUtil;
import com.drore.tdp.bo.ResourceDetailDevice;
import com.drore.tdp.bo.ResourceDetailGroup;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.constant.RedisKey;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import com.drore.tdp.service.CameraService;
import com.drore.tdp.webservice.ICommonServiceStub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:59.
 */
@Slf4j
@Service
public class CameraServiceImpl extends BaseApiService implements CameraService {
    @Value("${tdp.camera.params.host}")
    private String host;
    @Value("${tdp.camera.params.node-index-code}")
    private String nodeIndexCode;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QueryUtil queryUtil;

    @Override
    public ResponseBase syncCamera() {
        Long time = System.currentTimeMillis();
        log.info("监控同步开始:{}", DateTimeUtil.nowDateTimeString());
        List<CameraGroup> cameraGroups;
        List<CameraDevice> cameraDevices;
        ResponseBase responseBase;
        try {
            Map<String, List> cameraInfo = getCameraInfo();
            cameraGroups = cameraInfo.get("cameraGroup");
            cameraDevices = cameraInfo.get("cameraDevice");
            responseBase = queryUtil.saveOrUpdateCameraGroup(cameraGroups);
            if (!responseBase.isStatus()) {
                return error("监控同步失败");
            }
            responseBase = queryUtil.saveOrUpdateCameraDevice(cameraDevices);
            if (!responseBase.isStatus()) {
                return error("监控同步失败");
            }
        } catch (Exception e) {
            log.error("获取监控信息异常:{}", e);
            return error("监控同步失败");
        }
        time = System.currentTimeMillis() - time;
        log.info("监控同步结束：{},耗时:{}毫秒", DateTimeUtil.nowDateTimeString(), time);
        return success(time, "监控同步成功");
    }

    /**
     * 将响应过来的监控数据放入缓存
     */
    public void saveToRedis() {
        List<CameraDevice> cameraDevices;
        try {
            Map<String, List> cameraInfo = getCameraInfo();
            cameraDevices = cameraInfo.get("cameraDevice");
        } catch (Exception e) {
            log.error("获取监控信息异常:{}", e);
            return;
        }
        redisUtil.set(RedisKey.CAMERA_INFO, cameraDevices);
        log.info("监控信息缓存成功,共缓存:{}条监控数据", cameraDevices.size());
    }

    public Map<String, List> getCameraInfo() throws Exception {
        ICommonServiceStub.GetAllResourceDetailResponse dt;
        ICommonServiceStub is = new ICommonServiceStub(host);
        //获取全部组织资源
        ICommonServiceStub.GetAllResourceDetail detail = new ICommonServiceStub.GetAllResourceDetail();
        //资源类型： 1000资源组织;3000用户组织;4000应用;30000编码设备;110000解码设备;100000视频综合平台;50000监视屏组;
        detail.setResType(1000);
        //服务的INDEXCODE（预留参数，调用方的indexCode，可填任意String类型值）
        detail.setNodeIndexCode(nodeIndexCode);
        dt = is.getAllResourceDetail(detail);
        String responseResult = dt.get_return();
        JSONObject objectGroupResponse = XmlUtil.xml2json(responseResult);
        Integer sizeDevice = objectGroupResponse.getJSONObject("table").getJSONObject("head").getJSONObject("result").getInteger("size");
        List<ResourceDetailGroup> resourceDetails = new ArrayList<>();
        if (sizeDevice == 0) {
            throw new Exception("监控列表数据不存在");
        } else if (sizeDevice == 1) {
            ResourceDetailGroup resourceDetail = objectGroupResponse.getJSONObject("table").getJSONObject("rows").getObject("row", ResourceDetailGroup.class);
            resourceDetails.add(resourceDetail);
        } else {
            JSONArray array = objectGroupResponse.getJSONObject("table").getJSONObject("rows").getJSONArray("row");
            resourceDetails = JSONArray.parseArray(JSON.toJSONString(array), ResourceDetailGroup.class);
        }
        log.debug("组织信息:{}", resourceDetails);
        List<CameraGroup> cameraGroups = new ArrayList<>();
        List<CameraDevice> cameraDevices = new ArrayList<>();
        resourceDetails.stream().forEach(resourceDetail -> {
            Integer id = resourceDetail.getId();
            String indexCode = resourceDetail.getIndexCode();
            Integer leave = resourceDetail.getLeave();
            String orgName = resourceDetail.getOrgName();
            Integer parentId = resourceDetail.getParentId();
            CameraGroup cameraGroup = new CameraGroup();
            cameraGroup.setGroupNo(String.valueOf(id));
            cameraGroup.setGroupName(orgName);
            cameraGroup.setIndexCode(indexCode);
            cameraGroup.setLeaveNo(String.valueOf(leave));
            cameraGroup.setParentNo(String.valueOf(parentId));
            cameraGroup.setSeries("HK7600");
            cameraGroups.add(cameraGroup);
            List<CameraDevice> list = listCameraDeviceByOrgCode(indexCode, orgName);
            cameraDevices.addAll(list);
        });
        Map map = new HashMap(2);
        map.put("cameraGroup", cameraGroups);
        map.put("cameraDevice", cameraDevices);
        return map;
    }

    /**
     * 获取指定组织下全部资源
     *
     * @param orgCode
     * @param orgName
     * @return
     */
    private List<CameraDevice> listCameraDeviceByOrgCode(String orgCode, String orgName) {
        String time = DateTimeUtil.nowDateTimeString();
        ICommonServiceStub.GetAllResourceDetailByOrg gr = new ICommonServiceStub.GetAllResourceDetailByOrg();
        gr.setOrgCode(orgCode);
        gr.setResType(10000);
        gr.setNodeIndexCode(nodeIndexCode);
        ICommonServiceStub.GetAllResourceDetailByOrgResponse rt;
        try {
            ICommonServiceStub is = new ICommonServiceStub(host);
            rt = is.getAllResourceDetailByOrg(gr);
        } catch (RemoteException e) {
            throw new RuntimeException("获取指定组织" + orgCode + "--" + orgName + "下全部资源异常:{}", e);
        }
        String responseResult1 = rt.get_return();
        JSONObject objectDeviceResponse = XmlUtil.xml2json(responseResult1);
        Integer sizeDevice = objectDeviceResponse.getJSONObject("table").getJSONObject("head").getJSONObject("result").getInteger("size");
        List<ResourceDetailDevice> resourceDetailDevices = new ArrayList<>();
        if (sizeDevice <= 0) {
            log.info("{}-{} 组织下未添加监控", orgCode, orgName);
        } else if (sizeDevice == 1) {
            ResourceDetailDevice device = objectDeviceResponse.getJSONObject("table").getJSONObject("rows").getObject("row", ResourceDetailDevice.class);
            resourceDetailDevices.add(device);
        } else {
            JSONArray array = objectDeviceResponse.getJSONObject("table").getJSONObject("rows").getJSONArray("row");
            resourceDetailDevices = JSONArray.parseArray(JSON.toJSONString(array), ResourceDetailDevice.class);
        }
        log.debug("{}-{} 组织下监控信息:{}", orgCode, orgName, resourceDetailDevices);
        return resourceDetailDevices.stream().map(resourceDetailDevice -> {
            Integer id = resourceDetailDevice.getId();
            Integer orgId = resourceDetailDevice.getOrgId();
            String indexCode = resourceDetailDevice.getIndexCode();
            Integer cameraType = resourceDetailDevice.getCameraType();
            String deviceIp = resourceDetailDevice.getDeviceIp();
            Integer devicePort = resourceDetailDevice.getDevicePort();
            Integer channelNo = resourceDetailDevice.getChannelNo();
            String name = resourceDetailDevice.getName();
            Integer status = resourceDetailDevice.getStatus();
            Integer isOnline = resourceDetailDevice.getIsOnline();
            CameraDevice cameraDevice = new CameraDevice();
            cameraDevice.setGroupNo(String.valueOf(orgId));
            cameraDevice.setDeviceNo(String.valueOf(id));
            cameraDevice.setDeviceName(name);
            cameraDevice.setIndexCode(indexCode);
            cameraDevice.setDeviceTypeNo(cameraType);
            cameraDevice.setDeviceIp(deviceIp);
            cameraDevice.setDevicePort(devicePort);
            cameraDevice.setChannelNo(channelNo);
            cameraDevice.setStatus(status);
            cameraDevice.setIsOnline(isOnline);
            cameraDevice.setModifiedTime(time);
            return cameraDevice;
        }).collect(Collectors.toList());
    }
}
