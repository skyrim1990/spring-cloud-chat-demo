package com.tao.coreservice.client;

import com.tao.coreservice.util.JsonResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="authenticate-service")
public interface SessionClient {
  @PostMapping("/session/authenticate")
  JsonResponse authenticate(String token);
}
