package com.tao.authenticateservice.service.Impl;

import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.payload.LoginPayload;
import com.tao.authenticateservice.payload.RegistPayload;
import com.tao.authenticateservice.security.UserDetailsServiceImpl;
import com.tao.authenticateservice.service.SessionService;
import com.tao.authenticateservice.service.UserService;
import com.tao.authenticateservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {


  @Value("${system.expirTime}")
  private long expirTime;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;


  @Override
  public boolean authenticate(String token) throws Exception {
    if (null != token) {
      String userId = jwtUtil.getUserIdFromToken(token);
      long id = Long.parseLong(userId);
      User user = userService.findUserById(Long.valueOf(userId).longValue());
      if (userId != null && user != null) {
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUserId(id);

        if (jwtUtil.validateToken(token, userDetails)) {
          return true;
        }

      }

    }

    return false;
  }

  @Override
  public String login(LoginPayload payload) throws Exception {
    UsernamePasswordAuthenticationToken uToken = 
      new UsernamePasswordAuthenticationToken(payload.getPhonenumber(), payload.getPassword());

    try{
     Authentication authentication = authenticationManager.authenticate(uToken);
     User user = userService.findUserByPhonenumber(payload.getPhonenumber());
     String token = jwtUtil.generateToken(user);
     return token;
   } catch (DisabledException e) {
      throw new Exception("Use disabled !");
    } catch (BadCredentialsException e) {
      throw new Exception("Invalid Credentials !");
    }

    // SecurityContextHolder.getContext().setAuthentication(authentication);


  }

  @Override
  public String regist(RegistPayload payload) throws Exception {
    User user = userService.findUserByPhonenumber(payload.getPhonenumber());
    if(user != null) {
      throw new Exception("该手机号已注册 !");
    }
    user = userService.findUserByUsername(payload.getUsername());
    if(user != null) {
      throw new Exception("该用户名已存在 !");
    }
    user = new User();
    log.info(payload.toString());
    user.setPhonenumber(payload.getPhonenumber());
    user.setUsername(payload.getUsername());
    String password_digested = passwordEncoder.encode(payload.getPassword());
    user.setPasswordDigest(password_digested);
    user.setGender(payload.getGender());
    user.setAge(payload.getAge());
    user.setAvatar(payload.getAvatar());
    user.setActive(1);
    int result = userService.addUser(user);
    if(result == 1) {
      UsernamePasswordAuthenticationToken uToken = 
        new UsernamePasswordAuthenticationToken(payload.getPhonenumber(), payload.getPassword());
      Authentication  authentication = authenticationManager.authenticate(uToken);
      return jwtUtil.generateToken(user);
    } else {
      throw new Exception("注册失败 !");
    }
  }

  @Override
  public void logout(String token) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public UserDetails getUserDetails(String token) throws Exception {
    if (null != token) {
      String userId = jwtUtil.getUserIdFromToken(token);
      long id = Long.parseLong(userId);
      User user = userService.findUserById(Long.valueOf(userId).longValue());
      if (userId != null && user != null) {
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUserId(id);
        return userDetails;
      }

    }

    return null;

  }

  @Override
  public String authenticateAndGetPhonenumber(String token) throws Exception {
    if (null != token) {
      String userId = jwtUtil.getUserIdFromToken(token);
      long id = Long.parseLong(userId);
      User user = userService.findUserById(Long.valueOf(userId).longValue());
      if (userId != null && user != null) {
        return user.getPhonenumber();
      }

    }

    return null;
  }
  
}
