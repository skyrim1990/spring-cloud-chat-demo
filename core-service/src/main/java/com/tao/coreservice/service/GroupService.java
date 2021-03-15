package com.tao.coreservice.service;

import java.util.List;

import com.tao.coreservice.model.Chat;
import com.tao.coreservice.model.ChatGroup;
import com.tao.coreservice.model.User;

public interface GroupService {
  List<ChatGroup> findGroups(long id);
  int addGroup(User owner, String name, String ids);
  int addFriendToGroup(long ownerId, long groupId, long friendId);
  int removeFriendToGroup(long ownerId, long groupId, long friendId);
  int removeGroup(long ownerId, long groupId);
  List<ChatGroup> findGroupsByIds(String ids);
}
