package com.tao.authenticateservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistPayload {
  private String phonenumber;
  private String username;
  private String password;
  private int gender;
  private int age;
  private String avatar;
}
