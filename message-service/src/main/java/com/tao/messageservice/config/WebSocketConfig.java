package com.tao.messageservice.config;

import com.netflix.discovery.converters.Auto;
import com.tao.messageservice.channelInterceptor.CusHandshakeInterceptor;
import com.tao.messageservice.channelInterceptor.HttpHandshakeHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private HttpHandshakeHandler httpHandshakeHandler;

  @Autowired
  private CusHandshakeInterceptor cusHandshakeInterceptor;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .addInterceptors(cusHandshakeInterceptor)
        .setHandshakeHandler(httpHandshakeHandler)
        .setAllowedOrigins("*").withSockJS();
  }


}
