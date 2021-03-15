package com.tao.messageservice.client;

import com.tao.messageservice.util.JsonResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="authenticate-service")
public interface SessionClient {
  @PostMapping("/session/authenticateAndGetUsername")
  JsonResponse authenticateAndGetPhonenumber(String token);
}
