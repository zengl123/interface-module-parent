package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.domain.Pagination;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.DefaultValue;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import com.drore.tdp.domain.table.Table;
import com.drore.tdp.feign.CameraServiceImplFeign;
import com.drore.tdp.service.ICameraService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  14:26.
 */
@Service
public class CameraServiceImpl extends BaseApiService implements ICameraService {
    @Autowired
    private CameraServiceImplFeign feign;
    @Autowired
    private CloudQueryRunner runner;

    /**
     * 同步监控数据
     *
     * @return
     */
    @Override
    public ResponseBase update() {
        return feign.syncCamera();
    }

    @Override
    public ResponseBase getCameraDeviceInfo() {
        return feign.listCameraDeviceInfo();
    }

    /**
     * 根据指定条件查询监控列表(默认查询所有)
     *
     * @param requestBody
     * @return
     */
    @Override
    public ResponseBase getCameraGroupByRequestBody(JSONObject requestBody) {
        requestBody = requestBody == null ? new JSONObject() : requestBody;
        Integer currentPage = requestBody.getInteger("currentPage") == null ? DefaultValue.CURRENT_PAGE : requestBody.getInteger("currentPage");
        Integer pageSize = requestBody.getInteger("pageSize") == null ? DefaultValue.PAGE_SIZE : requestBody.getInteger("pageSize");
        Map map = new HashMap<>(requestBody.size());
        String series = requestBody.getString("series");
        //根据厂家查询
        if (StringUtils.isNotEmpty(series)) {
            map.put("series", series);
        }
        //根据id查询
        String id = requestBody.getString("id");
        if (StringUtils.isNotEmpty(id)) {
            map.put("id", id);
        }
        //根据列表编号查询
        String groupNo = requestBody.getString("groupNo");
        if (StringUtils.isNotEmpty(groupNo)) {
            map.put("group_no", groupNo);
        }
        //根据列表名称查询
        String groupName = requestBody.getString("groupName");
        if (StringUtils.isNotEmpty(groupName)) {
            map.put("group_name", groupName);
        }
        Pagination<CameraGroup> pagination = runner.queryListByExample(CameraGroup.class, Table.CAMERA_GROUP, map, currentPage, pageSize);
        if (pagination.getSuccess()) {
            return success(pagination.getData(), "查询监控列表成功");
        } else {
            return error("查询监控列表失败");
        }
    }
}
