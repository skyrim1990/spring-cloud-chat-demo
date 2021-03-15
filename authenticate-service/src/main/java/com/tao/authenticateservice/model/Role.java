package com.tao.authenticateservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("role")
public class Role {
  private long id;
  private String name;
  private long count;
  private Date createdAt;
  private Date updatedAt;
}
