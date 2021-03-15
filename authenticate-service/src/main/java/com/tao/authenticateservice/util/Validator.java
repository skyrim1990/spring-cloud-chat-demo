package com.tao.authenticateservice.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class Validator {
  public static JsonResponse validate(Errors errors) {
    Map<String, String> result = new HashMap<String, String>();
    List<ObjectError> objectErrors = errors.getAllErrors();
    if(objectErrors.isEmpty()) {
      return null;
    } else {
      objectErrors.stream().forEach((error) -> {
        String key;
        String msg;
        if(error instanceof FieldError ) {
          FieldError fe = (FieldError) error;
          key = fe.getField();
        } else {
          key = error.getObjectName();
        }
        msg = error.getDefaultMessage();
        result.put(key, msg);
      });
      return JsonResponse.error("参数错误", result);
    }
  }
}
