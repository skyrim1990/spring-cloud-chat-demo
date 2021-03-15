package com.tao.coreservice.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationPayload {
  private long receiverId;
  private String senderName;
  private String groupName;
  private long groupId;
  private int invitationType; // 0:好友请求 1:群邀请
  private int handled; // 0 未处理 // 已处理
 
}
