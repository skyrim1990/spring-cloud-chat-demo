package com.tao.coreservice.service;

import java.util.List;

import com.tao.coreservice.model.Chat;
import com.tao.coreservice.model.Invitation;
import com.tao.coreservice.model.User;

public interface FriendService {

  List<User> findUserByPhoneNumber(String param);
  List<User> findUserByUsername(String param);

  List<User> getFriends(long userId);
  User getFriendById(long id);

  List<Invitation> findInvitationByReceiverId(long id);

  int insertInvitation(Invitation invitation);
  int updateInvitation(Invitation invitation);

  int insertFriendShip(long userId, long friendId);
  int removeFriendShip(long userId, long friendId);
  List<Chat> findChats(long id);
}
