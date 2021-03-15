package com.tao.authenticateservice.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tao.authenticateservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  @Autowired
  private ObjectMapper mapper;

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
    
      resp.setContentType("application/json;charset=utf-8");
      PrintWriter out = resp.getWriter();
      String result = mapper.writeValueAsString(JsonResponse.error("无法访问 你缺少权限", null));
      out.write(result);
      out.flush();
      out.close();

	}
  
}
