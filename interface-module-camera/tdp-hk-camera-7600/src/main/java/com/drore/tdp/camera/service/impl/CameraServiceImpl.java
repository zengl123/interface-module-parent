package com.drore.tdp.camera.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drore.tdp.camera.service.ICameraService;
import com.drore.tdp.camera.webservice.ICommonServiceStub;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.common.utils.RedisUtil;
import com.drore.tdp.common.utils.XmlUtil;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:59.
 */
@Slf4j
@Service
public class CameraServiceImpl extends BaseApiService implements ICameraService {
    @Value("${tdp.camera.host}")
    private String host;
    @Value("${tdp.camera.nodeIndexCode}")
    private String nodeIndexCode;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseBase syncCamera() {
        log.info("监控同步开始:{}", DateTimeUtil.nowDateTimeString());
        return success();
    }

    /**
     * 将响应过来的监控数据放入缓存
     */
    public void set(List<CameraDevice> cameraDevices) {

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
        JSONArray rowGroup = objectGroupResponse.getJSONObject("table").getJSONObject("rows").getJSONArray("row");
        List<CameraGroup> cameraGroups = new ArrayList<>();
        List<CameraDevice> cameraDtos = new ArrayList<>();
        for (int i = 0; i < rowGroup.size(); i++) {
            JSONObject jsonGroup = (JSONObject) rowGroup.get(i);
            String orgCode = jsonGroup.getString("c_index_code");
            //获取指定组织下全部资源
            ICommonServiceStub.GetAllResourceDetailByOrgResponse rt;
            ICommonServiceStub.GetAllResourceDetailByOrg gr = new ICommonServiceStub.GetAllResourceDetailByOrg();
            gr.setOrgCode(orgCode);
            gr.setResType(10000);
            gr.setNodeIndexCode(nodeIndexCode);
            rt = is.getAllResourceDetailByOrg(gr);
            String responseResult1 = rt.get_return();
            JSONObject objectDeviceResponse = XmlUtil.xml2json(responseResult1);
            Integer sizeDevice = objectDeviceResponse.getJSONObject("table").getJSONObject("head").getJSONObject("result").getInteger("size");
            JSONArray rowDevice = new JSONArray();
            if (sizeDevice <= 0) {
                continue;
            } else if (sizeDevice == 1) {
                JSONObject device = objectDeviceResponse.getJSONObject("table").getJSONObject("rows").getJSONObject("row");
                rowDevice.add(device);
            } else {
                rowDevice = objectDeviceResponse.getJSONObject("table").getJSONObject("rows").getJSONArray("row");
            }
            String groupId = jsonGroup.getString("i_id");
            CameraGroup cameraGroup = new CameraGroup();
            cameraGroup.setGroupName(jsonGroup.getString("c_org_name"));
            cameraGroup.setGroupNo(groupId);
            cameraGroup.setSeries("HK7600");
            cameraGroups.add(cameraGroup);
            rowDevice.stream().forEach(objectDevice -> {
                JSONObject jsonDevice = (JSONObject) objectDevice;
                CameraDevice cameraDevice = new CameraDevice();
                //设备id
                String iid = jsonDevice.getString("i_id");
                //设备通道号
                Integer channelNo = jsonDevice.getInteger("i_channel_no");
                //设备是否在线
                String isOnline = jsonDevice.getString("i_is_online");
                //设备是否删除
                String iStatus = jsonDevice.getString("i_status");
                if (channelNo > 32) {
                    channelNo -= 32;
                }
                cameraDto.setDeviceId(iid);
                String indexCode = jsonDevice.getString("c_index_code");
                //组织id
                cameraDto.setCameraListId(jsonDevice.getString("i_org_id"));
                //设备内部唯一编码
                cameraDto.setIndexCode(indexCode);
                //设备ip
                cameraDto.setIpAddress(jsonDevice.getString("c_device_ip"));
                //设备端口
                cameraDto.setNetworkPort(jsonDevice.getInteger("i_device_port"));
                cameraDto.setChannelNo(channelNo);
                cameraDto.setCameraName(jsonDevice.getString("c_name"));
                cameraDto.setUserName(jsonDevice.getString("c_creator"));
                cameraDto.setStatus(iStatus);
                cameraDto.setIsOnline(isOnline);
                cameraDtos.add(cameraDto);
            });
        }
        Map map = new HashMap(2);
        map.put("cameraGroup", cameraGroups);
        map.put("cameraDevice", cameraDtos);
        return map;
    }
}
