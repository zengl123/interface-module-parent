package com.drore.tdp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  16:45.
 */
@Data
@ApiModel
public class BaseModel {
    @JSONField(name = "pkid")
    private String id;
    @JSONField(name = "create_time")
    @SerializedName(value = "create_time")
    @ApiModelProperty(hidden = true)
    private String createTime;
    @JSONField(name = "modified_time")
    @SerializedName(value = "modified_time")
    @ApiModelProperty(hidden = true)
    private String modifiedTime;
    @JSONField(name = "is_deleted")
    @SerializedName(value = "is_deleted")
    @ApiModelProperty(hidden = true)
    private String isDeleted;
}
