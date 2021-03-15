package com.tao.resourceservice.controller;

import com.tao.resourceservice.util.JsonResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @GetMapping({"/", "/index"})
  public JsonResponse index() {
    return JsonResponse.success("success", "resource service");
  }
  
}
