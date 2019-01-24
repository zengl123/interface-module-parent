package com.drore.tdp.domain.camera;

import com.drore.tdp.domain.BaseModel;
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
    private String groupNo;
    private String groupName;
    private String series;
}
