package com.drore.tdp.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  13:57.
 */
@Data
public class ResourceDetailDevice {
    @JSONField(name = "i_id")
    private Integer id;
    @JSONField(name = "i_org_id")
    private Integer orgId;
    @JSONField(name = "c_index_code")
    private String indexCode;
    @JSONField(name = "c_name")
    private String name;
    @JSONField(name = "i_channel_no")
    private Integer channelNo;
    @JSONField(name = "i_is_online")
    private Integer isOnline;
    @JSONField(name = "i_status")
    private Integer status;
    @JSONField(name = "i_camera_type")
    private Integer cameraType;
    @JSONField(name = "c_device_ip")
    private String deviceIp;
    @JSONField(name = "i_device_port")
    private Integer devicePort;
}
