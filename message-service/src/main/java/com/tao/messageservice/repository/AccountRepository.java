package com.tao.messageservice.repository;

import java.util.List;

import com.tao.messageservice.model.ChatGroup;
import com.tao.messageservice.model.User;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository {
  User findUserById(long id);
  List<User> findUsersByIds(String ids);
  ChatGroup findChatGroupById(long id);  
}
