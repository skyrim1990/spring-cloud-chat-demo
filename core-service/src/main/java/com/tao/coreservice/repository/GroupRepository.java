package com.tao.coreservice.repository;

import java.util.List;

import com.tao.coreservice.model.ChatGroup;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository {
  int addGroup(ChatGroup group);
  int removeGroup(@Param("id") long id);
  int updateGroupFriends(@Param("id") long id, @Param("fids") String fids);
  int addRelationShip(@Param("gId") long groupId, @Param("uId") long friendId);
  int removeRelationShip(@Param("gId") long groupId, @Param("uId") long friendId);
  List<ChatGroup> findGroupsByOwnerId(@Param("id") long id);
  List<ChatGroup> findGroupsByGroupIds(@Param("ids") String ids);
  ChatGroup findGroupById(@Param("id") long id);
}
