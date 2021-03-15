package com.tao.coreservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.coreservice.client.SessionClient;
import com.tao.coreservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

  @Autowired
  private SessionClient sessionClient;

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {


    JsonResponse jsonResponse;
    String token = req.getHeader("auth");
    if (token != null) {

      jsonResponse = sessionClient.authenticate(token);
      if(jsonResponse == null) {
        throw new Exception("Null result error");
      }
      if(jsonResponse.getStatus()) {
        log.info("Validate Successful");
        return true;
      } else {
        throw new Exception("Error Token");
      }
    }
    throw new Exception("Null Token Error");

  }
}
