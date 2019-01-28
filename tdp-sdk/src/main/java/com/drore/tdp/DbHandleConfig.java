package com.drore.tdp;

import com.drore.cloud.sdk.basic.CloudBasicConnection;
import com.drore.cloud.sdk.basic.CloudBasicDataSource;
import com.drore.cloud.sdk.basic.CloudPoolingConnectionManager;
import com.drore.cloud.sdk.client.CloudQueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 浙江卓锐科技有限公司
 * Date:2017-07-25
 * Description:构建基于云数据中心的数据操作类
 * Project:cloud-tdp-data-interface-v2.0
 *
 * @author ZENLIN
 */
@Component
@Configuration
public class DbHandleConfig {
    @Value("${tdp.cloud.url}")
    private String cloudUrl;
    @Value("${tdp.cloud.appId}")
    private String appId;
    @Value("${tdp.cloud.secret}")
    private String secret;
    @Value("${tdp.cloud.port}")
    private String port;

    /**
     * 具体操作bean,ioc的过程
     *
     * @return
     */
    @Bean(name = "CloudQueryRunner")
    public CloudQueryRunner getCloudQueryRunner() {
        CloudPoolingConnectionManager cloudPoolingConnectionManager = new CloudPoolingConnectionManager();
        cloudPoolingConnectionManager.setConnection(new CloudBasicConnection(cloudUrl, Integer.valueOf(port), appId, secret));
        CloudBasicDataSource cloudBasicDataSource = new CloudBasicDataSource();
        cloudBasicDataSource.setCloudPoolingConnectionManager(cloudPoolingConnectionManager);
        CloudQueryRunner cloudQueryRunner = new CloudQueryRunner();
        cloudQueryRunner.setDataSource(cloudBasicDataSource);
        return cloudQueryRunner;
    }
}
