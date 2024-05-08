package com.example.demo.services.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisService implements Cache {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Optional get(String key, Class clazz) {
        Object object = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(object);
    }

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void flush() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }
}
