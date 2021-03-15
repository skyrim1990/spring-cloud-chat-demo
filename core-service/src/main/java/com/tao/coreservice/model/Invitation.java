package com.tao.coreservice.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("invitation")
public class Invitation {

  private long id;
  private long senderId;
  private long receiverId;
  private long groupId;
  private String senderName;
  private String groupName;
  private int invitationType; // 0:好友请求 1:群邀请
  private int handled; // 0 未处理 // 已处理
  private int agree; // 0 不同意 1 同意
}
