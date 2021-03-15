package com.tao.authenticateservice.repository;

import java.util.List;

import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.model.UserRole;
import com.tao.authenticateservice.payload.PageParam;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
  public List<User> findAllUsers();
  public List<User> findUsers(
    @Param("name") String name,
    @Param("page") PageParam pageParam
  );
  public User findUserById(long id);
  public User findUserByPhonenumber(String phonenumber);
  public User findUserByUsername(String username);
  public long countUsers();
  public int activeUser(long id);
  public int deactiveUser(long id);
  public int addUser(User user);
  public int addRole(long userId, long roleId);
  public int removeRole(long id);
  public int updateUserProfile(
    @Param("id") long id, 
    @Param("name") String name, 
    @Param("avatar") String avatar
  );
  public List<UserRole> getRolesByUserId(long id);
}
