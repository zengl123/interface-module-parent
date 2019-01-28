package com.drore.tdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ZENLIN
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
public class TdpApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(TdpApiApplication.class, args);
	}
}

