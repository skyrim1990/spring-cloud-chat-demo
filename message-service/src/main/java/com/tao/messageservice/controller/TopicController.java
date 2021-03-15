package com.tao.messageservice.controller;

import com.tao.messageservice.util.CusMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TopicController {

  /*
  @MessageMapping("/send")
  public CusMessage MessageTopic(CusMessage msg) throws Exception {
    log.info("Send Message" + msg);
    return msg;
  }
  */
  
}
