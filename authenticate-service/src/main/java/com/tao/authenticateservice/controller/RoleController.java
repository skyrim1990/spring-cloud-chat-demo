package com.tao.authenticateservice.controller;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.json.JsonReadContext;
import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.payload.PageParam;
import com.tao.authenticateservice.payload.RoleSearchPayload;
import com.tao.authenticateservice.service.RoleService;
import com.tao.authenticateservice.util.JsonResponse;
import com.tao.authenticateservice.util.PageResult;

import org.checkerframework.common.reflection.qual.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/role")
@RestController
public class RoleController {

  @Autowired
  private RoleService roleService;

  @PostMapping("/findRoles")
  public JsonResponse findRoles() {
    
    List<Role> roles = roleService.findAllRoles();

    return JsonResponse.success("success", roles);
  }

  @PostMapping("/add/{name}")
  public JsonResponse addRole(@PathVariable("name") String roleName) {
    Role role = new Role();
    role.setName(roleName);
    int result = roleService.insertRole(role);
    if(result == 1) {
      return JsonResponse.success("新增角色成功 !", null);
    } else {
      return JsonResponse.error("新增角色失败 !", null);
    }
  }

  @DeleteMapping("/remove/{id}")
  public JsonResponse remove(@PathVariable long id) {
    Role role = roleService.findRoleById(id);
    if(role == null) {
      return JsonResponse.error("该角色不存在 !", null);
    }
    if(role.getCount() != 0) {
      return JsonResponse.error("无法删除 仍有用户拥有该角色 !", null);
    }
    int result = roleService.deleteRole(id);
    if(result == 1) {
      return JsonResponse.success("删除角色成功 !", null);
    } else {
      return JsonResponse.error("删除角色失败 !", null);
    }
  }

}
