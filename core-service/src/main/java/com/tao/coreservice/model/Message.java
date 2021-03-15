package com.tao.coreservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("message")
public class Message {
  private long id;
  private int messageType; // 0 好友消息 1 群消息 
  private long groupId; // 群聊ID message 为1是启用
  private long receiverId; // 接收者ID message 为0时启用
  private long senderId; // 发送人ID
  private String senderName; // 发送人消息
  private Date createdAt;
  private Date updatedAt;
}
