package com.tao.messageservice.service;

import java.util.List;

import com.tao.messageservice.model.User;

public interface AccountService {
  User findUserById(long id);
  List<User> findUsersByChatGroupId(long groupId);
}
