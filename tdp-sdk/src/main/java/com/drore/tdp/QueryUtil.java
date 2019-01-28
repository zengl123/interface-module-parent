package com.drore.tdp;

import com.drore.cloud.sdk.client.CloudQueryRunner;
import com.drore.cloud.sdk.domain.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  16:58.
 */
@Component
public class QueryUtil {
    @Autowired
    private CloudQueryRunner runner;

    public String deduplication(String sourceName, Map map) {
        Pagination<Map> pagination = runner.queryListByExample(sourceName, map);
        if (null != pagination && pagination.getCount() > 0) {
            return String.valueOf(pagination.getData().get(0).get("id"));
        } else {
            return null;
        }
    }
}
