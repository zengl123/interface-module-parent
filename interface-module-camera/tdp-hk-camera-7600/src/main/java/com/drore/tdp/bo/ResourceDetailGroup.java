package com.drore.tdp.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  11:43.
 */
@Data
public class ResourceDetailGroup {
    @JSONField(name = "i_id")
    private Integer id;
    @JSONField(name = "i_level")
    private Integer leave;
    @JSONField(name = "c_index_code")
    private String indexCode;
    @JSONField(name = "i_parent_id")
    private Integer parentId;
    @JSONField(name = "c_org_name")
    private String orgName;
}
