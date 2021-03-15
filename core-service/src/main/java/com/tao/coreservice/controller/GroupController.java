package com.tao.coreservice.controller;

import java.util.List;
import java.util.OptionalLong;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl.Option;
import com.google.common.base.Optional;
import com.tao.coreservice.model.ChatGroup;
import com.tao.coreservice.model.User;
import com.tao.coreservice.payload.AddGroupPayload;
import com.tao.coreservice.service.FriendService;
import com.tao.coreservice.service.GroupService;
import com.tao.coreservice.util.JsonResponse;
import com.tao.coreservice.util.JwtUtil;
import com.tao.coreservice.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

  @Autowired
  GroupService groupService;

  @Autowired
  FriendService friendService;

  @Autowired
  private JwtUtil jwtUtil;

  // 新增讨论组
  @PostMapping("/add")
  public JsonResponse addGroup(
    @RequestHeader("auth") String token,
    @RequestBody AddGroupPayload payload){
      OptionalLong userId = OptionalLong.of(
        Long.parseLong(jwtUtil.getUserIdFromToken(token))
      );

      List<Long> ids = Util.stringToIds(payload.getIds());

      ids.add(userId.getAsLong());

        if(userId.isPresent()) {
          Optional<User> user = Optional.of(friendService.getFriendById(userId.getAsLong()));
          if(user.isPresent()) {
            int result = groupService.addGroup(user.get(), payload.getName(), Util.idsToString(ids));
            if(result != 0) {
              return JsonResponse.success("群聊创建成功 !", null);
            }
          }
        }
      return JsonResponse.error("群聊创建失败 !", null);
  }

  // 获取讨论组
  @GetMapping("/search")
  public JsonResponse findGroups(@RequestHeader("auth") String token) {
    OptionalLong userId = OptionalLong.of(Long.parseLong(jwtUtil.getUserIdFromToken(token)));
    Optional<User> user = Optional.of(friendService.getFriendById(userId.getAsLong()));
    Optional<List<ChatGroup>> groups = Optional.of(groupService.findGroupsByIds(user.get().getGroups()));
    return JsonResponse.success("success", groups.get());
  }

  // 向讨论组中添加朋友
  @GetMapping("/addFriend/{fId}/{gId}")
  public JsonResponse addFriendToGroup(
    @PathVariable("fId") long fId,
    @PathVariable("gId") long gId,
    @RequestHeader("auth") String token
  ) {
    OptionalLong ownerId = OptionalLong.of(
      Long.parseLong(jwtUtil.getUserIdFromToken(token))
    );
 
    if(ownerId.isPresent()) {
      int result = groupService.addFriendToGroup(ownerId.getAsLong(), gId, fId);
      if(result == 1) {
        return JsonResponse.success("添加好友成功 !", null);
      }
    }
    return JsonResponse.error("添加好友失败 !", null);
  }

  // 从讨论组中移除好友
  @GetMapping("/removeFriend/{fId}/{gId}")
  public JsonResponse removeFriendToGroup(
    @PathVariable("fId") long fId,
    @PathVariable("gId") long gId,
    @RequestHeader("auth") String token
  ) {
    OptionalLong ownerId = OptionalLong.of(
      Long.parseLong(jwtUtil.getUserIdFromToken(token))
    );
 
    if(ownerId.isPresent()) {
      int result = groupService.removeFriendToGroup(ownerId.getAsLong(), gId, fId);
      if(result == 1) {
        return JsonResponse.success("删除好友成功 !", null);
      }
    }
    return JsonResponse.error("删除好友失败 !", null);
  }

  // 删除讨论组
  @GetMapping("/remove/{id}")
  public JsonResponse removeGroup(
    @PathVariable("id") long id,
    @RequestHeader("auth") String token
  ) {
    OptionalLong ownerId = OptionalLong.of(
      Long.parseLong(jwtUtil.getUserIdFromToken(token))
    );
 
    if(ownerId.isPresent()) {
      int result = groupService.removeGroup(ownerId.getAsLong(), id);
      if(result == 1) {
        return JsonResponse.success("删除好友成功 !", null);
      }
    }
    return JsonResponse.error("删除好友失败 !", null);
  }
}
