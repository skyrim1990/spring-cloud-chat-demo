package com.tao.authenticateservice.service;

import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.payload.LoginPayload;
import com.tao.authenticateservice.payload.RegistPayload;

import org.springframework.security.core.userdetails.UserDetails;

public interface SessionService {
  public boolean authenticate(String token) throws Exception;
  public String login(LoginPayload payload) throws Exception;
  public String regist(RegistPayload payload) throws Exception;
  public void logout(String token) throws Exception; 
  public UserDetails getUserDetails(String token) throws Exception;
  public String authenticateAndGetPhonenumber(String token) throws Exception;
}
