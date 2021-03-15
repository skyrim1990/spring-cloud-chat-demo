package com.tao.coreservice.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import com.tao.coreservice.util.JsonResponse;

import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RestController
public class IndexController {

  @GetMapping({"/", "/index"})
  public JsonResponse index() {
    log.info("index");
    return JsonResponse.success("success", "core service");
  }

}