package com.tao.messageservice.channelInterceptor;

import java.security.Principal;
import java.util.Collection;

import javax.security.auth.Subject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements Principal {
  /**
   *
   */
  private String name;

  public User(String arg) {
    this.name = arg;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object obj) {
    // TODO Auto-generated method stub
    if(obj instanceof User) {

      User user = (User) obj;
      if(this.name == user.getName()) {
        return true;
      } else {
        return false;
      }

    } else {
      return false;
    }
  }

  @Override
  public boolean implies(Subject subject) {
    // TODO Auto-generated method stub
    return Principal.super.implies(subject);
  }

}
