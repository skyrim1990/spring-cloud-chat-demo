package com.tao.resourceservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
	@Value("${static-file-location}")
	private String location;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
    log.info(location);
			registry
				.addResourceHandler("/static/*")
				.addResourceLocations("file:/home/skyrim1990/tmp/images/");
	} 
}
