package com.tao.authenticateservice.controller;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.tao.authenticateservice.payload.LoginPayload;
import com.tao.authenticateservice.payload.RegistPayload;
import com.tao.authenticateservice.service.SessionService;
import com.tao.authenticateservice.util.JsonResponse;
import com.tao.authenticateservice.util.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/session")
@RestController
public class SessionController {

  @Autowired
  private SessionService sessionService;

  @PostMapping("/regist")
  public JsonResponse regist(@RequestBody RegistPayload payload) throws Exception {
    payload.setAvatar("/img/header.jpeg");
    String token = sessionService.regist(payload);
    Map<String, String> result = new HashMap<>();
    result.put("token", token);
    return JsonResponse.success("success", result);
  }

  @PostMapping("/login")
  public JsonResponse login(@Valid @RequestBody LoginPayload payload, Errors errors) throws Exception {

    JsonResponse e = Validator.validate(errors);
    if(e != null) {
      return e;
    }

    String token = sessionService.login(payload);
    Map<String, String> result = new HashMap<>();
    result.put("token", token);
    return JsonResponse.success("success", result);

  }

  @PostMapping("/logout")
  public JsonResponse logout() {
    return JsonResponse.success("success", "logout");

  }

  @PostMapping("/authenticate")
  public JsonResponse authenticate(@RequestBody String token) throws Exception {
    log.info("Received Token: " + token);
    boolean result = sessionService.authenticate(token);
    log.info("Authenticate result: " + result);
    if(result) {
      return JsonResponse.success("success", "authenticate");
    } else {
      return JsonResponse.error("failed", "authenticate");
    }
  }

  @PostMapping("/authenticateAndGetUsername")
  public JsonResponse authenticateAndGetPhonenumber(@RequestBody String token) throws Exception {
    String phonenumber = sessionService.authenticateAndGetPhonenumber(token); 
    log.info("PhoneNumber: " + phonenumber);
    if( phonenumber != null) {
      return JsonResponse.success("success", phonenumber);
    } else {
      return JsonResponse.error("error", null);
    }

  }

  @PostMapping("/getUserDetails")
  public JsonResponse getUserDetails(@RequestBody String token) throws Exception {
    UserDetails userDetails = sessionService.getUserDetails(token);
    if(userDetails == null) {
      return JsonResponse.error("failed", "authenticate");
    } else {
      return JsonResponse.success("success", userDetails);
    }

  }
  
}
