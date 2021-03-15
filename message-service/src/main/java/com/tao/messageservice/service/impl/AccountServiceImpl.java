package com.tao.messageservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.tao.messageservice.model.ChatGroup;
import com.tao.messageservice.model.User;
import com.tao.messageservice.repository.AccountRepository;
import com.tao.messageservice.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository repository;

  @Override
  public User findUserById(long id) {
    Optional<User> user = Optional.of(
      repository.findUserById(id)
    );
    return user.get();
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public List<User> findUsersByChatGroupId(long groupId) {
    Optional<ChatGroup> group = 
      Optional.of(repository.findChatGroupById(groupId));

    Optional<List<User>> users =
      Optional.of(repository.findUsersByIds(group.get().getMembers()));
    
    return users.get();

  }

}
