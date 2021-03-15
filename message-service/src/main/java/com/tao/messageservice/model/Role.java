package com.tao.messageservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  private long id;
  private String name;
  private long count;
  private Date createdAt;
  private Date updatedAt;
}
