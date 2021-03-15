package com.tao.coreservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("chat")
public class Chat {
  private long id;
  private String lastMessage;
  private String members;
  private Date updatedAt;
}
