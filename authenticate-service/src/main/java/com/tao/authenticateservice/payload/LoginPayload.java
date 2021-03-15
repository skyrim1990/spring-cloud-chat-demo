package com.tao.authenticateservice.payload;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayload {
  @NotNull(message ="账号不能为空!")
  private String phonenumber;
  @NotNull(message = "密码不能为空!")
  private String password;  
}
