package com.drore.tdp.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/21  9:31.
 */
@Component
public class BeanUtil {
    /**
     * 任务线程池
     *
     * @return
     */
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor getPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        poolTaskExecutor.setCorePoolSize(5);
        //最大线程数
        poolTaskExecutor.setMaxPoolSize(1000);
        //空闲线程的存活时间
        poolTaskExecutor.setKeepAliveSeconds(30000);
        //队列最大长度
        poolTaskExecutor.setQueueCapacity(200);
        return poolTaskExecutor;
    }
}
