package com.drore.tdp;

import com.alibaba.fastjson.JSON;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.common.resp.RestMessage;
import com.drore.cloud.sdk.domain.Pagination;
import com.drore.tdp.common.base.BaseApiService;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.common.utils.DateTimeUtil;
import com.drore.tdp.domain.camera.CameraDevice;
import com.drore.tdp.domain.camera.CameraGroup;
import com.drore.tdp.domain.table.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  16:58.
 */
@Slf4j
@Component
public class QueryUtil extends BaseApiService {
    @Autowired
    private CloudQueryRunner runner;

    public String deduplication(String sourceName, Map map) {
        Pagination<Map> pagination = runner.queryListByExample(sourceName, map);
        if (null != pagination && pagination.getCount() > 0) {
            return String.valueOf(pagination.getData().get(0).get("id"));
        } else {
            return null;
        }
    }

    public ResponseBase saveOrUpdateCameraGroup(List<CameraGroup> cameraGroups) {
        if (CollectionUtils.isEmpty(cameraGroups)) {
            return error();
        }
        String time = DateTimeUtil.nowDateTimeString();
        List<CameraGroup> add = new ArrayList<>();
        List<CameraGroup> update = new ArrayList<>();
        cameraGroups.stream().forEach(cameraGroup -> {
            String groupNo = cameraGroup.getGroupNo();
            Map map = new HashMap(1);
            map.put("group_no", groupNo);
            String id = deduplication(Table.CAMERA_GROUP, map);
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

    public ResponseBase saveOrUpdateCameraDevice(List<CameraDevice> cameraDevices) {
        if (CollectionUtils.isEmpty(cameraDevices)) {
            return error();
        }
        String time = DateTimeUtil.nowDateTimeString();
        List<CameraDevice> add = new ArrayList<>();
        List<CameraDevice> update = new ArrayList<>();
        cameraDevices.stream().forEach(cameraDevice -> {
            String deviceNo = cameraDevice.getDeviceNo();
            String indexCode = cameraDevice.getIndexCode();
            Map map = new HashMap(1);
            map.put("device_no", deviceNo);
            map.put("index_code", indexCode);
            String id = deduplication(Table.CAMERA_DEVICE, map);
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
    private Integer clearByModified(String tableName, String beginTime) {
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
