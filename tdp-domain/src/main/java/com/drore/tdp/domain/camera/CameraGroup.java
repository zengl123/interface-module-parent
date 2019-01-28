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
public class CameraGroup extends BaseModel {
    @JSONField(name = "group_no")
    @SerializedName("group_no")
    private String groupNo;
    @JSONField(name = "group_name")
    @SerializedName("group_name")
    private String groupName;
    private String series;
    @JSONField(name = "leave_no")
    @SerializedName("leave_no")
    private String leaveNo;
    @JSONField(name = "parent_no")
    @SerializedName("parent_no")
    private String parentNo;
    @JSONField(name = "index_code")
    @SerializedName("index_code")
    private String indexCode;
}
