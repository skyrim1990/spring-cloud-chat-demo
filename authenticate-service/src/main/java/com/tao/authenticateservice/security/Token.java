package com.tao.authenticateservice.security;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
  private String username;
  private Timestamp expiration;
  public boolean isExpired() {
    return this.expiration.getTime() < new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 10000).getTime();
  }  
  
}
