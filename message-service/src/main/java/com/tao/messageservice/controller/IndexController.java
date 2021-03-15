package com.tao.messageservice.controller;

import java.util.HashMap;
import java.util.Map;

import com.tao.messageservice.util.CusMessage;
import com.tao.messageservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class IndexController {


  @Autowired
  private SimpMessageSendingOperations messageTemplate;
  
  
  @GetMapping("/")
  public String index() {
    return "Message Service !";
  }

  @GetMapping("/test/{phonenumber}")
  public JsonResponse index(@PathVariable("phonenumber") String phonenumber) {

    CusMessage message = new CusMessage();
    message.setContent("this is a message");

    log.info("Phonenumber: " + phonenumber);
    messageTemplate.convertAndSendToUser(phonenumber, "/topic/system", message);

    return JsonResponse.success("测试消息已发送", null);
  }
}