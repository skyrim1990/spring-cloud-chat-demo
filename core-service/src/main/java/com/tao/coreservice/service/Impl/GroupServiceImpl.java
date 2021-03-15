package com.tao.coreservice.service.Impl;

import java.util.List;
import java.util.Optional;

import com.tao.coreservice.model.Chat;
import com.tao.coreservice.model.ChatGroup;
import com.tao.coreservice.model.User;
import com.tao.coreservice.repository.ChatRepository;
import com.tao.coreservice.repository.FriendRepository;
import com.tao.coreservice.repository.GroupRepository;
import com.tao.coreservice.service.GroupService;
import com.tao.coreservice.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

  @Autowired
  GroupRepository groupRepository;

  @Autowired
  FriendRepository friendRepository;

  @Autowired
  ChatRepository chatRepository;

  @Override
  public List<ChatGroup> findGroups(long id) {
    return groupRepository.findGroupsByOwnerId(id);
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int addGroup(User owner, String name, String ids) {

    List<User> users = friendRepository.findUsersByIds(ids);

    ChatGroup group = new ChatGroup();
    group.setId(0);
    group.setOwnerId(owner.getId());
    group.setOwnerName(owner.getUsername());
    group.setGroupName(name);
    group.setMembers(ids);
    group.setLastMessage("欢迎 可以开始聊天了");

    groupRepository.addGroup(group);
    log.info("groupId: " + group.getId());
    if (group.getId() != 0) {
      Long groupId = group.getId();
      for (User user : users) {
        log.info("Groups: " + user.getGroups());

        List<Long> userIds = Util.stringToIds(user.getGroups());
        userIds.add(groupId);
        log.info("GString: " + Util.idsToString(userIds));
        user.setGroups(Util.idsToString(userIds));

        friendRepository.updateUserGroupList(user);
        log.info(groupId + "----" + user.getId());
        groupRepository.addRelationShip(groupId, user.getId());
      }
    } else {
      return 0;
    }
    return 1;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int addFriendToGroup(long ownerId, long groupId, long friendId) {
    ChatGroup group = groupRepository.findGroupById(groupId);
    if (group.getOwnerId() != ownerId) {
      return 0;
    }
    User user = friendRepository.findUserById(friendId);
    List<Long> groupIds = Util.stringToIds(user.getGroups());
    groupIds.add(groupId);
    user.setGroups(Util.idsToString(groupIds));

    List<Long> memeberIds = Util.stringToIds(group.getMembers());
    memeberIds.add(friendId);

    friendRepository.updateUserGroupList(user);
    groupRepository.updateGroupFriends(groupId, Util.idsToString(memeberIds));
    groupRepository.addRelationShip(groupId, friendId);
    return 1;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int removeFriendToGroup(long ownerId, long groupId, long friendId) {
    ChatGroup group = groupRepository.findGroupById(groupId);
    if (group.getOwnerId() != ownerId) {
      return 0;
    }
    User user = friendRepository.findUserById(friendId);
    List<Long> groupIds = Util.stringToIds(user.getGroups());
    groupIds.remove(groupId);
    user.setGroups(Util.idsToString(groupIds));

    List<Long> memeberIds = Util.stringToIds(group.getMembers());
    memeberIds.remove(friendId);

    friendRepository.updateUserGroupList(user);
    groupRepository.updateGroupFriends(groupId, Util.idsToString(memeberIds));
    groupRepository.removeRelationShip(groupId, friendId);

    return 1;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1)
  public int removeGroup(long ownerId, long groupId) {
    Optional<ChatGroup> group = Optional.ofNullable(groupRepository.findGroupById(ownerId));

    if (group.isPresent() && group.get().getOwnerId() != ownerId) {
      Optional<List<User>> users = Optional.ofNullable(friendRepository.findUsersByIds(group.get().getMembers()));
      if (users.isPresent()) {
        for (User user : users.get()) {
          List<Long> userIds = Util.stringToIds(user.getGroups());
          userIds.remove(groupId);
          user.setGroups(Util.idsToString(userIds));
          friendRepository.updateUserGroupList(user);
        }
      }
      groupRepository.removeGroup(groupId);
      return 1;
    }
    return 0;
  }

  @Override
  public List<ChatGroup> findGroupsByIds(String ids) {
    return groupRepository.findGroupsByGroupIds(ids);
  }

}
