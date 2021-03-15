package com.tao.coreservice.repository;

import java.util.List;

import com.tao.coreservice.model.Invitation;
import com.tao.coreservice.model.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendRepository {
  public List<User> findUserByPhonenumber(
    @Param("phonenumber") String param
  );

  public List<User> findUserByUsername(
    @Param("username") String param
  );

  public User findUserById(
    @Param("id") long id
  );

  public List<User> findUsersByIds(
    @Param("ids") String ids
  );

  public int insertInvitation(
    @Param("invitation") Invitation invitation
  );

  public int updateInvitation(
    @Param("invitation") Invitation invitation
  );

  public List<Invitation> findInvitationByReceiverId(
    @Param("id") long id 
  );

  public int updateUserFriendList(
    @Param("user") User user
  );

  public int updateUserGroupList(
    @Param("user") User user
  );

  public int insertFriendShip(
    @Param("userId") long userId,
    @Param("friendId") long friendId
  );

  public int removeFriendShip(
    @Param("userId") long userId,
    @Param("friendId") long friendId
  );

}
