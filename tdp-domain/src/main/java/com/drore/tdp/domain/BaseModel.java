package com.drore.tdp.domain;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  16:45.
 */
@Data
public class BaseModel {
    private String id;
    private String createTime;
    private String modifiedTime;
    private String isDeleted;
}
