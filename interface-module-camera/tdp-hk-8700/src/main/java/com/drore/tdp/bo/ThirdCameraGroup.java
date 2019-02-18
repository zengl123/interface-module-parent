package com.drore.tdp.bo;

import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  15:45.
 */
@Data
public class ThirdCameraGroup {
    private String regionUuid;
    private String name;
    private String parentUuid;
    private String parentNodeType;
    private String isParent;
}
