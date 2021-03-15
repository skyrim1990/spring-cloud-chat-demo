package com.tao.coreservice.controller;

import java.util.List;
import java.util.regex.Pattern;

import com.tao.coreservice.model.Chat;
import com.tao.coreservice.model.Invitation;
import com.tao.coreservice.model.User;
import com.tao.coreservice.payload.InvitationPayload;
import com.tao.coreservice.service.FriendService;
import com.tao.coreservice.util.JsonResponse;
import com.tao.coreservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {

  @Autowired
  private FriendService friendService;

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping("/getChats")
  public JsonResponse findChats(@RequestHeader("auth") String token) {
    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 
    List<Chat> chats = friendService.findChats(userId);
    return JsonResponse.success("success", chats);
  }

  // 搜索朋友
  @GetMapping("/search/{param}")
  public JsonResponse searchFriend(@PathVariable("param") String param) {
    Pattern pattern = Pattern.compile("^[1]\\d{10}$");
    boolean isPhoneNumber = pattern.matcher(param).matches();

    List<User> users;

    if(isPhoneNumber) {
      users = friendService.findUserByPhoneNumber(param);
    } else {
      users = friendService.findUserByUsername(param);
    }

    return JsonResponse.success("", users);

  }

  // 拉取好友列表
  @GetMapping("/getFriends")
  public JsonResponse getFriends(@RequestHeader("auth") String token) {
    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 
    List<User> friends = friendService.getFriends(userId);
    return JsonResponse.success("msg", friends);
  }
  @GetMapping("/getInvitations")
  public JsonResponse getInvitation(@RequestHeader("auth") String token) {
    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 
    List<Invitation> invitations = friendService.findInvitationByReceiverId(userId);
    return JsonResponse.success("success", invitations);

  }

  // 请求添加好友
  @PostMapping("/inviteFriend")
  public JsonResponse inviteFriend(
    @RequestHeader("auth") String token, 
    @RequestBody InvitationPayload payload
    ) {

    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 
    Invitation invitation = new Invitation();
    invitation.setSenderId(userId);
    invitation.setReceiverId(payload.getReceiverId());

    invitation.setSenderName(payload.getSenderName());

    invitation.setInvitationType(0);
    invitation.setHandled(0);

    int result = friendService.insertInvitation(invitation);

    // todo 发送消息给接收者

    if(result == 1) {
      return JsonResponse.success("请求发送成功 !", null);
    } else {
      return JsonResponse.success("请求发送失败 !", null);
    }

  }

  @GetMapping("/deniedFriend/{invitationId}")
  public JsonResponse deniedFriend(
    @RequestHeader("auth") String token, 
    @PathVariable("invitationId") Long id 
  ) {
    Invitation invitation = new Invitation();
    invitation.setId(id);
    invitation.setAgree(0);
    invitation.setHandled(1);

    int result = friendService.updateInvitation(invitation);

    if(result == 1) {
      return JsonResponse.success("操作成功 !", null);
    } else {
      return JsonResponse.error("操作失败 !", null);
    }
  }


  // 同意添加好友
  @GetMapping("/acceptFriend/{friendId}/{invitationId}")
  public JsonResponse acceptFriend(
    @RequestHeader("auth") String token, 
    @PathVariable("friendId") Long friendId,
    @PathVariable("invitationId") Long id 
  ) {
    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 

    Invitation invitation = new Invitation();
    invitation.setId(id);
    invitation.setAgree(1);
    invitation.setHandled(1);

    friendService.updateInvitation(invitation);



    int result = friendService.insertFriendShip(userId, friendId);

    if(result == 1) {
      return JsonResponse.success("好友添加成功 !", null);
    } else {
      return JsonResponse.success("好友添加失败 !", null);
    }

  }

  // 删除好友
  @DeleteMapping("/remove/{friendId}")
  public JsonResponse removeFriend(
    @RequestHeader("auth") String token,
    @PathVariable("friendId") Long friendId
  ) {
    long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token)); 
    int result = friendService.removeFriendShip(userId, friendId);

    if(result == 1) {
      return JsonResponse.success("好友删除成功 !", null);
    } else {
      return JsonResponse.success("好友删除失败 !", null);
    }

  }

  
}
