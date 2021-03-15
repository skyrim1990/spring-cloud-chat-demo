package com.tao.authenticateservice.controller;

import java.util.List;

import javax.ws.rs.HeaderParam;

import com.netflix.ribbon.proxy.annotation.Http.Header;
import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.model.UserRole;
import com.tao.authenticateservice.payload.PageParam;
import com.tao.authenticateservice.payload.UserSearchPayload;
import com.tao.authenticateservice.service.UserService;
import com.tao.authenticateservice.util.JsonResponse;
import com.tao.authenticateservice.util.JwtUtil;
import com.tao.authenticateservice.util.PageResult;

import org.apache.ibatis.annotations.Delete;
import org.aspectj.util.GenericSignatureParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  JwtUtil jwtUtil;

  @GetMapping("/findAllUser")
  public JsonResponse findAllUsers() {
    List<User> users = userService.findAllUsers();
    return JsonResponse.success("success", users);
  }

  @GetMapping("/getUserInfoByToken")
  public JsonResponse getUserInfoByToken(@RequestHeader("auth") String token) {

    String id = jwtUtil.getUserIdFromToken(token);
    User user = userService.findUserById(Long.parseLong(id));

    log.info("/getUserInfoByToken");

    if( user == null ) {
      return JsonResponse.error("用户不存在 !", null);
    } else {
      user.setPasswordDigest("");
      return JsonResponse.success("success", user);
    }


  }

  @GetMapping("updateProfile/{newName}/{avatar}")
  public JsonResponse updateUserProfile(
    @RequestHeader("auth") String token, 
    @PathVariable("newName") String newName,
    @PathVariable("avatar") String avatar
  ) {
    String id = jwtUtil.getUserIdFromToken(token);
    int result = userService.updateUserProfile(Long.parseLong(id), newName, avatar);
    if(result == 1) {
      return JsonResponse.success("个人信息更新成功!", null);
    } else {
      return JsonResponse.success("个人信息更新失败!", null);
    }

  }

  @GetMapping("/getById/{id}")
  public JsonResponse getUserById(@PathVariable long id) {
    User user = userService.findUserById(id);
    if(user == null) {
      return JsonResponse.error("用户不存在 !", null);
    } else {
      return JsonResponse.error("success", user);
    }
  }

  @GetMapping("/active/{id}")
  public JsonResponse active(@PathVariable long id) {
    int result = userService.activeUser(id);
    if(result == 1) {
      return JsonResponse.success("账号激活成功!", null);
    } else {
      return JsonResponse.success("账号激活失败!", null);
    }
  }

  @GetMapping("/deactive/{id}")
  public JsonResponse deActive(@PathVariable long id) {
    int result = userService.deactiveUser(id);
    if(result == 1) {
      return JsonResponse.success("账号禁用成功!", null);
    } else {
      return JsonResponse.success("账号禁用失败!", null);
    }
  }

  @PostMapping("/findUsers")
  public JsonResponse findUsers(@RequestBody UserSearchPayload payload) {
    List<User> users = userService.findUsers(payload.getName(), 
      new PageParam(payload.getPageIndex(), payload.getPageSize()));

    long total = userService.countUsers();

    return JsonResponse.success("success", 
      new PageResult(
        payload.getPageIndex(), total, users));
  }

  @GetMapping("/getRoles/{id}")
  public JsonResponse getRoles(@PathVariable("id") long userId) {
    log.info("User Id: " + userId);
    List<UserRole> roles =  userService.getRolesByUserId(userId);
    return JsonResponse.success("success", roles);
  }

  @GetMapping("/addRole/{userId}/{roleId}")
  public JsonResponse addRole(@PathVariable long roleId, @PathVariable long userId) {
    int result = userService.addRole(userId, roleId);
    if(result == 1) {
      return JsonResponse.success("新增角色成功!", null);
    } else {
      return JsonResponse.success("新增角色失败!", null);
    }
  }

  @DeleteMapping("/removeUserRole/{userRoleId}")
  public JsonResponse removeUserRole(@PathVariable("userRoleId") long id) {
    int result = userService.removeRole(id);
    if(result == 1) {
      return JsonResponse.success("删除角色成功!", null);
    } else {
      return JsonResponse.success("删除角色失败!", null);
    }
  }
  
}
