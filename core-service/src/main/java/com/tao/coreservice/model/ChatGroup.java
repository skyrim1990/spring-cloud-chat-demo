package com.tao.coreservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("chatgroup")
public class ChatGroup {
  private long id;  
  private long ownerId;
  private String ownerName;
  private String groupName;
  private String members;
  private String lastMessage;
  private Date updatedAt;
}
