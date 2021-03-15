package com.tao.eurekapoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaPointApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaPointApplication.class, args);
	}

}
