package com.drore.tdp.utils;

import com.alibaba.fastjson.JSONObject;
import com.drore.cloud.sdk.common.util.MD5Util;
import com.drore.tdp.common.utils.HttpClientUtil;
import com.drore.tdp.constant.Hk8700Constant;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/15  11:10.
 */
@Slf4j
public class Hk8700Util {

    /**
     * 获取默认用户UUID
     *
     * @return
     */

    public static String getDefaultUserUuid(String host, String appKey, String secret) {
        String path = Hk8700Constant.GET_DEFAULT_USER_UUID;
        JSONObject param = new JSONObject();
        param.put("time", System.currentTimeMillis());
        param.put("appkey", appKey);
        String url = postBuildToken(host, path, param, secret);
        JSONObject response = HttpClientUtil.httpPost(url, param);
        String defaultUuid;
        if (null != response) {
            defaultUuid = response.getString("data");
        } else {
            defaultUuid = "";
        }
        log.info("[响应结果-默认用户id] {}", defaultUuid);
        return defaultUuid;
    }

    public static final String postBuildToken(String host, String path, JSONObject param, String secret) {
        StringBuilder url = new StringBuilder();
        url.append(host).append(path);
        String jsonString = JSONObject.toJSONString(param);
        StringBuilder builder = new StringBuilder();
        String md5String = builder.append(path).append(jsonString).append(secret).toString();
        String token = MD5Util.getMD5Str(md5String).toUpperCase();
        url.append("?token=").append(token);
        return url.toString();
    }
}
