package com.tao.messageservice.controller;

import java.util.List;
import java.util.OptionalLong;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.netflix.discovery.converters.Auto;
import com.tao.messageservice.model.User;
import com.tao.messageservice.service.AccountService;
import com.tao.messageservice.util.CusMessage;
import com.tao.messageservice.util.JsonResponse;
import com.tao.messageservice.util.JwtUtil;
import com.tao.messageservice.util.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class MessageController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private SimpMessageSendingOperations mesageTemplate;

  @Autowired
  private AccountService accountService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping("/systemMessage")
  public JsonResponse systemMessage(
    @Valid @RequestBody CusMessage msg, Errors errors) throws Exception {
    JsonResponse e = Validator.validate(errors);
    if(e != null) {
      return e;
    } else {
      mesageTemplate.convertAndSend("/topic/system", msg);
      return JsonResponse.success("消息推送成功", null);
    }

  }
  @MessageMapping("/sendGroup")
  public JsonResponse sendToGroup(CusMessage msg ) throws JsonProcessingException {
    log.info("SendToGroup");
    Optional<String> id = Optional.of(jwtUtil.getUserIdFromToken(msg.getFrom()));

    msg.setFrom(id.get());
    log.info(msg.toString());
    log.info(
      accountService.findUsersByChatGroupId(Long.parseLong(msg.getGroupId())).toString()
    );
    Optional<List<User>> users = Optional.of(
      accountService.findUsersByChatGroupId(Long.parseLong(msg.getGroupId()))
    );


    String jsonMsg = objectMapper.writeValueAsString(msg);

    users.get().stream().forEach((User user) -> {
      mesageTemplate.convertAndSendToUser(
        user.getPhonenumber(), 
        "/topic/single", 
        jsonMsg
      );
    });

    return JsonResponse.success("消息推送成功", null);
  }


  @MessageMapping("/send")
  public JsonResponse sendToUser(
    CusMessage msg
  ) throws JsonProcessingException{
    log.info("SendToUser");
    Optional<String> id = Optional.of(jwtUtil.getUserIdFromToken(msg.getFrom()));

    msg.setFrom(id.get());
    Optional<User> sender = Optional.of(accountService.findUserById(Long.parseLong(id.get())));
    Optional<User> target = Optional.of(accountService.findUserById(Long.parseLong(msg.getTo())));


    String jsonMsg = objectMapper.writeValueAsString(msg);
    log.info(jsonMsg);

      mesageTemplate.convertAndSendToUser(
        sender.get().getPhonenumber(), 
        "/topic/single", 
        jsonMsg
      );
      mesageTemplate.convertAndSendToUser(
        target.get().getPhonenumber(), 
        "/topic/single", 
        jsonMsg
      );



      return JsonResponse.success("消息推送成功", null);
   
  }

  
}
