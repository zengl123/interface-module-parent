package com.drore.tdp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description Elasticsearch连接配置类
 * @Project
 * @Package
 * @ClassName ElasticsearchConfig
 * @Author 曾灵
 * @QQ|Email 3195690389|17363645521@163.com
 * @Date 2018-09-14 7:56
 * @Version 1.0
 * @Modified By
 */


@Slf4j
@Configuration
public class ElasticsearchConfig {
    @Value("${elasticsearch.ip}")
    private String[] ipAddress;

    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";

    @Bean(name = "restClient")
    public RestClient restClient() {
        HttpHost[] hosts = Arrays.stream(ipAddress).map(this::makeHttpHost).filter(Objects::nonNull).toArray(HttpHost[]::new);
        log.debug("hosts:{}", Arrays.toString(hosts));
        return RestClient.builder(hosts).build();
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient() {
        HttpHost[] hosts = Arrays.stream(ipAddress).map(this::makeHttpHost).filter(Objects::nonNull).toArray(HttpHost[]::new);
        log.debug("hosts:{}", Arrays.toString(hosts));
        return new RestHighLevelClient(RestClient.builder(hosts));
    }

    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            log.error("elasticsearch 初始化失败");
            return null;
        }
    }
}
