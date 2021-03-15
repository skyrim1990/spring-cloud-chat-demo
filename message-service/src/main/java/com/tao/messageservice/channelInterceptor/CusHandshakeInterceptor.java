package com.tao.messageservice.channelInterceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tao.messageservice.client.SessionClient;
import com.tao.messageservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CusHandshakeInterceptor implements HandshakeInterceptor {

  @Autowired
  private SessionClient sessionClient;

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) throws Exception {

    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
    HttpServletRequest req = servletRequest.getServletRequest();
    String token_str = req.getParameter("token");
    log.info("Param:  " + req.getParameter("token"));

    if(sessionClient == null) {
      log.error("SessionClient is Null");
      return false;
    }

    if(token_str != null) {
      JsonResponse resp = sessionClient.authenticateAndGetPhonenumber(token_str);
      log.info("Phonenumber: " + resp.getData().toString());

      if(resp.getStatus()) {
        attributes.put("phonenumber", (String)resp.getData());
      } else {
        log.error("Can not get phonenumber");
        return  false;
      }
      return true;
    } else {
      log.error("Token is Null");
    }
    return false;

  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Exception exception) {

    log.info("After Handshake");

  }
  
}
