package com.tao.resourceservice.handler;

import javax.servlet.http.HttpServletRequest;

import com.tao.resourceservice.util.JsonResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value=Exception.class)
  @ResponseBody
  public JsonResponse defaultErrorHandler(HttpServletRequest req, Exception e) {

    JsonResponse resp = new JsonResponse();

    resp.setStatus(false);

    if(e instanceof NoHandlerFoundException) {
      resp.setCode(404);
    } else {
      resp.setCode(500);
    }
    resp.setMsg(e.getMessage());
    resp.setData(null);

    return resp;
  }
  
}
