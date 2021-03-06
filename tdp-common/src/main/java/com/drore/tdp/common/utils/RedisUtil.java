package com.drore.tdp.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  15:17.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public void set(String key, List value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value, Long expireTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
