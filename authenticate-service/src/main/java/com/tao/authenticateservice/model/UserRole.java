package com.tao.authenticateservice.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("userRole")
public class UserRole {
  private long id;
  private long userId;
  private long roleId;
  private String roleName;
}
