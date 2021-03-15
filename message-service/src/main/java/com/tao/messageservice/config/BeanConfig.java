package com.tao.messageservice.config;

import com.tao.messageservice.channelInterceptor.CusHandshakeInterceptor;
import com.tao.messageservice.channelInterceptor.HttpHandshakeHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

  @Bean
  public HttpHandshakeHandler httpHandshakeHandler() {
    return new HttpHandshakeHandler();
  }

  @Bean
  public CusHandshakeInterceptor cusHandshakeInterceptor() {
    return new CusHandshakeInterceptor();
  }
  @Bean
  public RestTemplate getRestTemplate() {

    return new RestTemplate();
  }
  
}
