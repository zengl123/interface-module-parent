package com.drore.tdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 描述:网关服务
 * 项目名:my-shop-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/14  10:09.
 */
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class ZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer.class, args);
    }
}
