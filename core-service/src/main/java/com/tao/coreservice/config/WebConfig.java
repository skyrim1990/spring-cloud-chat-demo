package com.tao.coreservice.config;

import com.tao.coreservice.interceptor.GlobalInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  public GlobalInterceptor globalInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration ir = registry.addInterceptor(globalInterceptor);
    // 拦截所有请求
    ir.addPathPatterns("/");
  }

  
}
