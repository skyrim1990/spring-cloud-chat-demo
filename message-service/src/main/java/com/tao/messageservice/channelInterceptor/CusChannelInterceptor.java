package com.tao.messageservice.channelInterceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CusChannelInterceptor implements ChannelInterceptor {

  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    MessageHeaders headers = message.getHeaders();

    headers.forEach((key, value) -> {
      log.info(key + " : " + value);
    });

    // TODO Auto-generated method stub
    ChannelInterceptor.super.postSend(message, channel, sent);
  }
  
}
