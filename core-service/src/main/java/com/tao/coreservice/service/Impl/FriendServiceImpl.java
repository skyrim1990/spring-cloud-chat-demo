package com.tao.coreservice.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.netflix.discovery.converters.Auto;
import com.tao.coreservice.model.Chat;
import com.tao.coreservice.model.Invitation;
import com.tao.coreservice.model.User;
import com.tao.coreservice.repository.ChatRepository;
import com.tao.coreservice.repository.FriendRepository;
import com.tao.coreservice.service.FriendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

  @Autowired
  private FriendRepository friendRepository;

  @Autowired
  private ChatRepository chatRepository;


  @Override
  public List<User> findUserByPhoneNumber(String param) {
    return friendRepository.findUserByPhonenumber(param);
  }

  @Override
  public List<User> findUserByUsername(String param) {
    return friendRepository.findUserByUsername(param);
  }

  @Override
  public List<User> getFriends(long userId) {
    User user = friendRepository.findUserById(userId);
    log.info(user.toString()); 
    if(user == null || user.getFriends() == null || user.getFriends().length() == 0) {
      return new ArrayList<User>();
    } else {
      log.info("find Users");
      return friendRepository.findUsersByIds(user.getFriends());
    }
  }

  @Override
  public int insertInvitation(Invitation invitation) {
    return friendRepository.insertInvitation(invitation);
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int insertFriendShip(long userId, long friendId) {
    User user = friendRepository.findUserById(userId);
    User friend = friendRepository.findUserById(friendId);
    String uIds = "";
    String fIds = "";
    String ucIds = "";
    String fcIds = "";

    Chat chat = new Chat();
    chat.setLastMessage("添加好友成功 可以开始聊天了");
    chat.setMembers(user.getId() + "," + friend.getId());

    chatRepository.addChat(chat);

    if(user.getChats() == null || user.getChats().isEmpty()) {
      ucIds = String.valueOf(chat.getId());
    } else {
      ucIds = user.getChats() + "," + chat.getId();
    }

    if(friend.getChats() == null || friend.getChats().isEmpty()) {
      fcIds = String.valueOf(chat.getId());
    } else {
      fcIds = friend.getChats() + "," + chat.getId();
    }


    if(user.getFriends() == null || user.getFriends().isEmpty()) {
      uIds = String.valueOf(friendId);
    } else {
      uIds = user.getFriends() + "," + friendId;
    }

    if(friend.getFriends() == null || friend.getFriends().isEmpty() ) {
      fIds = String.valueOf(userId);
    } else {
      fIds = friend.getFriends() + "," + userId;
    }

    friend.setChats(fcIds);
    friend.setFriends(fIds);

    user.setChats(ucIds);
    user.setFriends(uIds);


    friendRepository.updateUserFriendList(user);
    friendRepository.updateUserFriendList(friend);
    return friendRepository.insertFriendShip(userId, friendId);

  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int removeFriendShip(long userId, long friendId) {
    String uIds = "";
    String fIds = "";
    String ucIds = "";
    String fcIds = "";

    User user = friendRepository.findUserById(userId);
    User friend = friendRepository.findUserById(friendId);
    Chat chat = chatRepository.findChatByMembers(
      userId+ "," + friendId, friendId + "," + userId
    );

    String[] userFriends = user.getFriends().split(",");
    List<String> userFriendList = Arrays.asList(userFriends).stream().collect(Collectors.toList());
    userFriendList.remove(String.valueOf(friendId));

    if(userFriendList.size() == 0 ) {
      uIds = "";
    } else if(userFriendList.size() == 1 ) {
      uIds = userFriendList.get(0).toString();
    } else {
      uIds = (userFriendList).stream().reduce("", (String a, String b) ->{
        return a + "," + b;
      });
    }

    String[] friendFriends = friend.getFriends().split(",");

    List<String> friendFriendList = Arrays.asList(friendFriends).stream().collect(Collectors.toList());
    friendFriendList.remove(String.valueOf(userId));

    if(friendFriendList.size() == 0) {
      fIds = "";
    } else if(friendFriendList.size() == 1 ) {
      fIds = friendFriendList.get(0).toString();
    } else {
      fIds = (friendFriendList).stream().reduce("", (String a, String b) ->{
        return a + "," + b;
      });
    }



    // Remove Friend Chats

    String[] friendChats = friend.getChats().split(",");


    List<String> friendChatList = Arrays.asList(friendChats).stream().collect(Collectors.toList());
    friendChatList.remove(String.valueOf(chat.getId()));

    if(friendChatList.size() == 0) {
      fcIds = "";
    } else if(friendChatList.size() == 1 ) {
      fcIds = friendChatList.get(0).toString();
    } else {
      fcIds = (friendChatList).stream().reduce("", (String a, String b) ->{
        return a + "," + b;
      });
    }

    // Remove User Chat
    String[] userChats = user.getChats().split(",");

    List<String> userChatList = Arrays.asList(userChats).stream().collect(Collectors.toList());
    userChatList.remove(String.valueOf(chat.getId()));

    if(userChatList.size() == 0) {
      ucIds = "";
    } else if(userChatList.size() == 1 ) {
      ucIds = userChatList.get(0).toString();
    } else {
      ucIds = (userChatList).stream().reduce("", (String a, String b) ->{
        return a + "," + b;
      });
    }






    friend.setFriends(fIds);
    friend.setChats(fcIds);

    user.setChats(ucIds);
    user.setFriends(uIds);


    friendRepository.updateUserFriendList(user);
    friendRepository.updateUserFriendList(friend);
    return friendRepository.removeFriendShip(userId, friendId);

  }

  @Override
  public List<Invitation> findInvitationByReceiverId(long id) {
    return friendRepository.findInvitationByReceiverId(id);
  }

  @Override
  public int updateInvitation(Invitation invitation) {
    return friendRepository.updateInvitation(invitation);
  }

  @Override
  public User getFriendById(long id) {
    return friendRepository.findUserById(id);
  }
  
  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public List<Chat> findChats(long id) {
    User user = friendRepository.findUserById(id);
    return chatRepository.findChatsByIds(user.getChats());
  }

}
