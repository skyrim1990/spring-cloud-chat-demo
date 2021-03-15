package com.tao.coreservice.config;

import com.tao.coreservice.interceptor.GlobalInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BeanConfig {

  @Bean
  public GlobalInterceptor globalInterceptor() {
    return new GlobalInterceptor();
  }


  @Bean
  public RestTemplate getRestTemplate() {

    log.info("init restTemplate");
    return new RestTemplate();
  }
  
}
