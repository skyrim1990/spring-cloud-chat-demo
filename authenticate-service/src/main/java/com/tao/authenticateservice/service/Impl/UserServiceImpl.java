package com.tao.authenticateservice.service.Impl;

import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.model.UserRole;
import com.tao.authenticateservice.payload.PageParam;
import com.tao.authenticateservice.repository.RoleRepository;
import com.tao.authenticateservice.repository.UserRepository;
import com.tao.authenticateservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<User> findAllUsers() {
    return repository.findAllUsers();
  }

  @Override
  public User findUserById(long id) {
    return repository.findUserById(id);
  }

  @Override
  public User findUserByPhonenumber(String phonenumber) {
    return repository.findUserByPhonenumber(phonenumber);
  }

  @Override
  public User findUserByUsername(String username) {
    return repository.findUserByUsername(username);
  }

  @Override
  public int activeUser(long id) {
    return repository.activeUser(id);
  }

  @Override
  public int deactiveUser(long id) {
    return repository.deactiveUser(id);
  }

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1)
  public int addUser(User user) {
    Role customer = roleRepository.findRoleByName("customer");
    repository.addUser(user);
    repository.addRole(user.getId(), customer.getId());
    roleRepository.increase(customer.getId());
    return 1;
  }

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1)
  public int addRole(long userId, long roleId) {
    repository.addRole(userId, roleId);
    roleRepository.increase(roleId);
    return 1;
  }

  @Override
  public List<User> findUsers(String name, PageParam pageParam) {
    return repository.findUsers(name, pageParam);
  }

  @Override
  public long countUsers() {
    return repository.countUsers();
  }

  @Override
  public int removeRole(long id) {
    return repository.removeRole(id);
  }

  @Override
  public List<UserRole> getRolesByUserId(long id) {
    return repository.getRolesByUserId(id);
  }

  @Override
  public int updateUserProfile(long id, String name, String avatar) {
    return repository.updateUserProfile(id, name, avatar);
  }
  
}
