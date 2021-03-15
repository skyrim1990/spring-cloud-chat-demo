package com.tao.messageservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResponse {
  private String msg;
  private Boolean status;
  private int code;
  private Object data;

  public static JsonResponse success(String msg, Object data) {
    return new JsonResponse(msg, true, 2000, data);
  }

  public static JsonResponse error(String msg, Object data) {
    return new JsonResponse(msg, false, 5000, data);
  }
}
