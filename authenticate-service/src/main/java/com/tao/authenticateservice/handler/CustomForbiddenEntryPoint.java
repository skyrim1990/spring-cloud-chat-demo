package com.tao.authenticateservice.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tao.authenticateservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomForbiddenEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private ObjectMapper mapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse resp, AuthenticationException authException)
      throws IOException, ServletException {
    
    resp.setContentType("application/json;charset=utf-8");
    PrintWriter out = resp.getWriter();
    String result = mapper.writeValueAsString(JsonResponse.error("无法访问 你需求先登录", null));
    out.write(result);
    out.flush();
    out.close();

  }
  
}
