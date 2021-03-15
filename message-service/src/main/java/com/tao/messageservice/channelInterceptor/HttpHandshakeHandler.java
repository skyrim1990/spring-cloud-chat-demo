package com.tao.messageservice.channelInterceptor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpHandshakeHandler extends DefaultHandshakeHandler {
  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
      String phonenumber = (String)attributes.get("phonenumber");
      log.info("GetPhoneNumber: " + phonenumber);
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
      UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
          new User(phonenumber), null,  new ArrayList<>());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(
        servletRequest.getServletRequest()
      ));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      return (Principal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

  }
  
}
