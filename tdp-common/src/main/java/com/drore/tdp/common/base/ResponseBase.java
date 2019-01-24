package com.drore.tdp.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Project my-shopping-parent
 * @Package com.zl.study.common
 * @ClassName ResponseBase
 * @Author 曾灵
 * @QQ|Email 3195690389|17363645521@163.com
 * @Date 2018-10-14 下午1:54
 * @Version 1.0
 * @Modified By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBase implements Serializable {
    @ApiModelProperty(value = "响应状态码:200表示响应成功,500表示响应失败", name = "code")
    private int code;
    @ApiModelProperty(value = "响应状态:true表示响应成功,false表示响应失败", name = "status")
    private boolean status;
    @ApiModelProperty(value = "响应消息", name = "message")
    private String message;
    @ApiModelProperty(value = "响应结果", name = "data")
    private Object data;
}
