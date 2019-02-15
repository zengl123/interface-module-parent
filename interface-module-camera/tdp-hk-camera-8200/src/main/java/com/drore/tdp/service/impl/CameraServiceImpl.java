package com.drore.tdp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.drore.tdp.common.base.ResponseBase;
import com.drore.tdp.constant.Hk8200Constant;
import com.drore.tdp.service.CameraService;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drore.tdp.constant.Hk8200Constant.FIND_CONTROL_UNIT_PAGE;


/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  10:44.
 */
@Slf4j
@Service
public class CameraServiceImpl implements CameraService {
    @Value("tdp.camera.params.host")
    private String host;
    @Value("tdp.camera.params.appKey")
    private String appKey;
    @Value("tdp.camera.params.secret")
    private String secret;

    /**
     * 同步监控数据
     *
     * @return
     */
    @Override
    public ResponseBase syncCamera() {
        return null;
    }

    public Map<String, List> getCameraInfo() {
        return null;
    }


    /**
     * get请求的查询参数
     * 分页获取组织树
     *
     * @return
     */
    private List findControlUnitPage() throws Exception {
        Map<String, String> query = new HashMap<>(2);
        //第几页开始，起始值0
        query.put("start", "0");
        //每页大小
        query.put("size", "1000");
        List list = new ArrayList<>();
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", FIND_CONTROL_UNIT_PAGE);
            }
        };
        String doGetArtemis = ArtemisHttpUtil.doGetArtemis(path, query, null, null);
        JSONObject json = JSONObject.parseObject(doGetArtemis);
        String msg = json.getString("msg");
        String code = json.getString("code");
        if (Hk8200Constant.SUCCESS_CODE.equals(code)) {
            //将array数组转换成字符串
            String data = JSONObject.toJSONString(json.getJSONArray("data"), SerializerFeature.WriteClassName);
            list = JSONObject.parseArray(data);
        } else {
            log.info(msg);
        }
        return list;
    }

}
