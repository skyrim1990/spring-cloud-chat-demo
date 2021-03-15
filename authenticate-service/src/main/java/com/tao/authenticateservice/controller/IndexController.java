package com.tao.authenticateservice.controller;

import com.tao.authenticateservice.util.JsonResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  
  @GetMapping({"/", "/index"})
  public JsonResponse index() {
    return JsonResponse.success("success", "authentication service");
  }

}
