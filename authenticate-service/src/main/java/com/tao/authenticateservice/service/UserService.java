package com.tao.authenticateservice.service;

import java.util.List;

import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.model.UserRole;
import com.tao.authenticateservice.payload.PageParam;

import org.apache.ibatis.annotations.Param;

public interface UserService {
  public List<User> findAllUsers();
  public List<User> findUsers(
    @Param("name") String name,
    @Param("pageParam") PageParam pageParam
  );
  public long countUsers(); 
  public User findUserById(long id);
  public User findUserByPhonenumber(String phonenumber);
  public User findUserByUsername(String username);
  public int activeUser(long id);
  public int deactiveUser(long id);
  public int addUser(User user);
  public int addRole(long userId, long roleId);
  public int removeRole(long id);
  public List<UserRole> getRolesByUserId(long id);
  public int updateUserProfile(long id, String name, String avatar );

}
