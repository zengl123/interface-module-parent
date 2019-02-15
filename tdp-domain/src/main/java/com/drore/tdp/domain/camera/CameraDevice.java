package com.drore.tdp.domain.camera;

import com.alibaba.fastjson.annotation.JSONField;
import com.drore.tdp.domain.BaseModel;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:22.
 */
@Data
public class CameraDevice extends BaseModel {
    @JSONField(name = "group_no")
    @SerializedName(value = "group_no")
    private String groupNo;
    @JSONField(name = "device_no")
    @SerializedName(value = "device_no")
    private String deviceNo;
    @JSONField(name = "device_name")
    @SerializedName(value = "device_name")
    private String deviceName;
    @JSONField(name = "index_code")
    @SerializedName(value = "index_code")
    private String indexCode;
    @JSONField(name = "device_type_no")
    @SerializedName(value = "device_type_no")
    private Integer deviceTypeNo;
    private transient String deviceTypeName;
    @JSONField(name = "device_ip")
    @SerializedName(value = "device_ip")
    private String deviceIp;
    @JSONField(name = "device_port")
    @SerializedName(value = "device_port")
    private Integer devicePort;
    @JSONField(name = "channel_no")
    @SerializedName(value = "channel_no")
    private Integer channelNo;
    private Integer status;
    @JSONField(name = "is_online")
    @SerializedName(value = "is_online")
    private Integer isOnline;
    @JSONField(name = "preview_parameter")
    @SerializedName(value = "preview_parameter")
    private String previewParameter;
    @JSONField(name = "playback_parameter")
    @SerializedName(value = "playback_parameter")
    private String playbackParameter;
}
