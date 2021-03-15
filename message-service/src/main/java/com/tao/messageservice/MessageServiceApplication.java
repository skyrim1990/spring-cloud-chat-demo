package com.tao.messageservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Repository;

@EnableFeignClients("com.tao.messageservice.client")
@SpringBootApplication
@MapperScan(
	basePackages = "com.tao.messageservice",
	annotationClass = Repository.class
)
@EnableDiscoveryClient
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

}
