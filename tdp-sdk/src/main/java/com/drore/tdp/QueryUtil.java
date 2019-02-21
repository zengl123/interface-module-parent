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
import com.drore.tdp.domain.flow.PassengerFlowDevice;
import com.drore.tdp.domain.flow.PassengerFlowRecord;
import com.drore.tdp.domain.park.CarParkDevice;
import com.drore.tdp.domain.park.CarParkRecord;
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

    private final static Integer SIZE = 1000;

    public String deduplication(String sourceName, Map map) {
        Pagination<Map> pagination = runner.queryListByExample(sourceName, map);
        if (null != pagination && pagination.getCount() > 0) {
            return String.valueOf(pagination.getData().get(0).get("id"));
        } else {
            return null;
        }
    }

    public ResponseBase saveOrUpdateCameraGroup(List<CameraGroup> cameraGroups) {
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
     * 删除平台已删除的停车场
     *
     * @param time
     */
    private void deleteOldCarParkDevice(String time) {
        Integer integer = clearByModified(Table.CAR_PARK_DEVICE, time);
        if (integer > 0) {
            log.info("删除无效停车场设备成功,共删除:{}个", integer);
        } else {
            log.info("平台停车场设备未发生变化");
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

    /****************************************停车场********************************************/
    public ResponseBase saveOrUpdateCarParkDevice(List<CarParkDevice> CarParkDevices) {
        String time = DateTimeUtil.nowDateTimeString();
        List<CarParkDevice> add = new ArrayList<>();
        List<CarParkDevice> update = new ArrayList<>();
        CarParkDevices.stream().forEach(CarParkDevice -> {
            String deviceNo = CarParkDevice.getDeviceNo();
            Map map = new HashMap(1);
            map.put("device_no", deviceNo);
            String id = deduplication(Table.CAR_PARK_DEVICE, map);
            if (StringUtils.isEmpty(id)) {
                add.add(CarParkDevice);
            } else {
                CarParkDevice.setId(id);
                update.add(CarParkDevice);
            }
        });
        if (CollectionUtils.isNotEmpty(add)) {
            RestMessage insertBatch = runner.insertBatch(Table.CAR_PARK_DEVICE, JSON.toJSON(add));
            if (insertBatch != null && insertBatch.isSuccess()) {
                log.info("停车场设备信息新增成功,共新增:{}条数据", add.size());
            } else {
                log.error("新增停车场设备信息失败:{}", insertBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有新增停车场设备");
        }
        if (CollectionUtils.isNotEmpty(update)) {
            RestMessage updateBatch = runner.updateBatch(Table.CAR_PARK_DEVICE, JSON.toJSON(update));
            if (updateBatch != null && updateBatch.isSuccess()) {
                log.info("停车场设备信息更新成功,共更新:{}条数据", update.size());
            } else {
                log.error("停车场设备信息更新失败:{}", updateBatch.getMessage());
                return error();
            }
        } else {
            log.info("没有更新停车场设备");
        }
        deleteOldCarParkDevice(time);
        return success();
    }

    public ResponseBase saveCarParkRecord(List<CarParkRecord> carParkRecords) {
        RestMessage insertBatch = runner.insertBatch(Table.CAR_PARK_RECORD, JSON.toJSON(carParkRecords));
        if (insertBatch.isSuccess()) {
            log.info("新增过车记录成功,共新增:{}条数据", carParkRecords.size());
            return success();
        } else {
            log.error("新增过车记录失败:{}", insertBatch.getMessage());
            return error();
        }
    }


    public ResponseBase saveOrUpdatePassengerFlowDevice(PassengerFlowDevice passengerFlowDevice) {
        String deviceNo = passengerFlowDevice.getDeviceNo();
        Map map = new HashMap(1);
        map.put("device_no", deviceNo);
        String id = deduplication(Table.PASSENGER_FLOW_DEVICE, map);
        if (StringUtils.isEmpty(id)) {
            RestMessage insert = runner.insert(Table.PASSENGER_FLOW_DEVICE, JSON.toJSON(passengerFlowDevice));
            if (insert.isSuccess()) {
                return success();
            } else {
                log.error("[新增客流监控点数据失败]");
                return error();
            }
        } else {
            RestMessage update = runner.update(Table.PASSENGER_FLOW_DEVICE, id, JSON.toJSON(passengerFlowDevice));
            if (update.isSuccess()) {
                return success();
            } else {
                log.error("[更新客流监控点数据失败]");
                return error();
            }
        }
    }

    /*************************************监控客流********************************************/

    public ResponseBase savePassengerFlowRecord(List<PassengerFlowRecord> passengerFlowRecords) {
        ResponseBase responseBase = batchSave(Table.CAR_PARK_RECORD, passengerFlowRecords);
        if (responseBase.isStatus()) {
            log.info("新增客流监控数据记录成功,共新增:{}条数据", passengerFlowRecords.size());
            return success();
        } else {
            log.error("新增客流监控数据记录失败:{}", responseBase.getMessage());
            return error();
        }
    }

    private ResponseBase batchSave(String sourcesName, List data) {
        int size = data.size();
        int count = size / SIZE;
        String message = "";
        if (count > 0) {
            boolean flag = true;
            for (int i = 0; i <= count; i++) {
                int end = (i + 1) * SIZE > size ? size : (i + 1) * SIZE;
                List d = data.subList(i * SIZE, end);
                RestMessage insertBatch = runner.insertBatch(sourcesName, JSON.toJSON(d));
                if (!insertBatch.isSuccess()) {
                    flag = false;
                    message = insertBatch.getMessage();
                    break;
                }
            }
            if (flag) {
                return success();
            } else {
                return error(message);
            }
        } else {
            RestMessage insertBatch = runner.insertBatch(sourcesName, JSON.toJSON(data));
            if (insertBatch.isSuccess()) {
                return success();
            } else {
                return error(insertBatch.getMessage());
            }
        }
    }

    /**
     * 获取数据同步时间
     *
     * @return
     */
    public String getSyncTime(String code) {
        Map map = new HashMap(1);
        map.put("code", code);
        Pagination<Map> pagination = runner.queryListByExample(Table.SYNC_TIME_CONFIG, map);
        if (pagination.getSuccess() && pagination.getCount() > 0) {
            return String.valueOf(pagination.getData().get(0).get("sync_time"));
        } else {
            log.error("同步时间未设置");
            return null;
        }
    }

    /**
     * 新增|更新同步时间配置
     *
     * @param map
     * @return
     */
    public ResponseBase saveOrUpdateSyncTime(Map map) {
        Map mapNew = new HashMap(1);
        mapNew.put("code", map.get("code"));
        String id = deduplication(Table.SYNC_TIME_CONFIG, mapNew);
        if (StringUtils.isEmpty(id)) {
            RestMessage insert = runner.insert(Table.SYNC_TIME_CONFIG, JSON.toJSON(map));
            if (insert.isSuccess()) {
                return success("新增同步时间配置成功");
            } else {
                return error("新增同步时间配置失败");
            }
        } else {
            RestMessage update = runner.update(Table.SYNC_TIME_CONFIG, id, JSON.toJSON(map));
            if (update.isSuccess()) {
                return success("更新同步时间配置成功");
            } else {
                return error("更新同步时间配置失败");
            }
        }
    }

    /**
     * 根据监控点id获取监控点信息
     *
     * @param cameraUuid
     * @return
     */
    public CameraDevice getCameraDeviceByCameraUuid(String cameraUuid) {
        Map map = new HashMap(1);
        map.put("index_code", cameraUuid);
        Pagination<CameraDevice> pagination = runner.queryListByExample(CameraDevice.class, Table.CAMERA_DEVICE, map);
        if (pagination != null && pagination.getCount() > 0) {
            return pagination.getData().get(0);
        } else {
            log.info("客流监控点-监控点id:{} 未匹配到对应的监控设备信息", cameraUuid);
            return null;
        }
    }
}