package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.common.resp.RestMessage;
import com.drore.cloud.sdk.domain.Pagination;
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
import com.drore.tdp.domain.table.Table;
import com.drore.tdp.service.CameraService;
import com.drore.tdp.webservice.ICommonServiceStub;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private CloudQueryRunner runner;


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
            responseBase = saveOrUpdateCameraGroup(cameraGroups);
            if (!responseBase.isStatus()) {
                return error("监控同步失败");
            }
            responseBase = saveOrUpdateCameraDevice(cameraDevices);
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
     * 获取实时监控设备信息
     *
     * @return
     */
    @Override
    public ResponseBase listCameraDeviceInfo() {
        List<CameraDevice> cameraDevices = (List)redisUtil.getObject(RedisKey.CAMERA_INFO);
        return success(cameraDevices, "获取实时监控设备信息成功");
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

    private ResponseBase saveOrUpdateCameraGroup(List<CameraGroup> cameraGroups) {
        String time = DateTimeUtil.nowDateTimeString();
        List<CameraGroup> add = new ArrayList<>();
        List<CameraGroup> update = new ArrayList<>();
        cameraGroups.stream().forEach(cameraGroup -> {
            String groupNo = cameraGroup.getGroupNo();
            Map map = new HashMap(1);
            map.put("group_no", groupNo);
            String id = queryUtil.deduplication(Table.CAMERA_GROUP, map);
            if (StringUtils.isEmpty(id)) {
                add.add(cameraGroup);
            } else {
                cameraGroup.setId(id);
                update.add(cameraGroup);
            }
        });
        if (CollectionUtils.isNotEmpty(add)) {
            RestMessage insertBatch = runner.insertBatch(Table.CAMERA_GROUP, JSON.toJSON(add));
            if (insertBatch != null && insertBatch.isSuccess()) {
                log.info("新增监控列表成功,共新增:{}条数据", add.size());
            } else {
                log.error("新增监控列表失败:{}", insertBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有新增监控列表");
        }
        if (CollectionUtils.isNotEmpty(update)) {
            RestMessage updateBatch = runner.updateBatch(Table.CAMERA_GROUP, JSON.toJSON(update));
            if (updateBatch != null && updateBatch.isSuccess()) {
                log.info("更新监控列表成功,共更新:{}条数据", update.size());
            } else {
                log.error("更新监控列表失败:{}", updateBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有更新监控列表");
        }
        deleteOldCameraGroup(time);
        return success();
    }

    private ResponseBase saveOrUpdateCameraDevice(List<CameraDevice> cameraDevices) {
        String time = DateTimeUtil.nowDateTimeString();
        List<CameraDevice> add = new ArrayList<>();
        List<CameraDevice> update = new ArrayList<>();
        cameraDevices.stream().forEach(cameraDevice -> {
            String deviceNo = cameraDevice.getDeviceNo();
            String indexCode = cameraDevice.getIndexCode();
            Map map = new HashMap(1);
            map.put("device_no", deviceNo);
            map.put("index_code", indexCode);
            String id = queryUtil.deduplication(Table.CAMERA_DEVICE, map);
            if (StringUtils.isEmpty(id)) {
                add.add(cameraDevice);
            } else {
                cameraDevice.setId(id);
                update.add(cameraDevice);
            }
        });
        if (CollectionUtils.isNotEmpty(add)) {
            RestMessage insertBatch = runner.insertBatch(Table.CAMERA_DEVICE, JSON.toJSON(add));
            if (insertBatch != null && insertBatch.isSuccess()) {
                log.info("监控设备信息新增成功,共新增:{}条数据", add.size());
            } else {
                log.error("新增监控信息失败:{}", insertBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有新增监控设备");
        }
        if (CollectionUtils.isNotEmpty(update)) {
            RestMessage updateBatch = runner.updateBatch(Table.CAMERA_DEVICE, JSON.toJSON(update));
            if (updateBatch != null && updateBatch.isSuccess()) {
                log.info("监控设备信息更新成功,共更新:{}条数据", update.size());
            } else {
                log.error("监控设备信息更新失败:{}", updateBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有更新监控设备");
        }
        deleteOldCameraDevice(time);
        return success();
    }

    /**
     * 删除平台已删除的监控列表
     *
     * @param time
     */
    private void deleteOldCameraGroup(String time) {
        Integer integer = clearByModified(Table.CAMERA_GROUP, time);
        if (integer > 0) {
            log.info("删除无效监控列表成功,共删除:{}个", integer);
        } else {
            log.info("平台监控列表未发生变化");
        }
    }

    /**
     * 删除平台已删除的监控
     *
     * @param time
     */
    private void deleteOldCameraDevice(String time) {
        Integer integer = clearByModified(Table.CAMERA_DEVICE, time);
        if (integer > 0) {
            log.info("删除无效监控设备成功,共删除:{}个", integer);
        } else {
            log.info("平台监控设备未发生变化");
        }
    }

    /**
     * 清除无效数据
     *
     * @param tableName
     * @param beginTime
     * @return
     */
    public Integer clearByModified(String tableName, String beginTime) {
        StringBuilder builder = new StringBuilder();
        builder.append("select id from ")
                .append(tableName)
                .append(" where is_deleted='N' and modified_time<'")
                .append(beginTime)
                .append("'");
        Pagination<Map> sql = runner.sql(builder.toString(), 1, 10000);
        if (sql.getCount() > 0) {
            List<Map> data = sql.getData();
            data.stream().forEach(map -> {
                String id = String.valueOf(map.get("id"));
                runner.delete(tableName, id);
            });
            return sql.getCount();
        } else {
            return 0;
        }
    }
}
