package com.tao.messageservice.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

  @Autowired
  private RedisTemplate redisTemplate = null;

  @PostConstruct
  public void init() {
    initRedisTemplate();
  }
  
  public void initRedisTemplate() {
    RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(stringSerializer);
  }
  
}
