package com.tao.coreservice.repository;

import java.util.List;

import com.tao.coreservice.model.Chat;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository {
  List<Chat> findChatsByIds(String ids);
  int addChat(Chat chat);
  int removeChat(@Param("id") long id);
  Chat findChatByMembers(
    @Param("member_a") String members_a,
    @Param("member_b") String members_b
  );
  int updateChat(Chat chat);
}
