package com.tao.coreservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("user")
public class User {
  private long id;
  private String username;
  private String phonenumber;
  private String passwordDigest;
  private String friends;
  private String groups;
  private String chats;
  private int age; 
  private int gender; // 0:女 1:男
  private int active; // 0:false 1:true
  private String avatar;
  private Date createdAt;
  private Date updatedAt; 
}
