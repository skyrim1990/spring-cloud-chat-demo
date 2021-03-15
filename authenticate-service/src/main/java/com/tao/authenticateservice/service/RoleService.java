package com.tao.authenticateservice.service;

import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.payload.PageParam;

import org.apache.ibatis.annotations.Param;

public interface RoleService {
  public List<Role> findRoles(@Param("name") String name, @Param("pageParam") PageParam pageParam);
  public List<Role> findAllRoles();
  public Role findRoleById(long id);
  public Role findRoleByName(String name);
  public int increase(long id);
  public int decrease(long id);
  public int insertRole(Role role);
  public int deleteRole(long id);  
  public long countRoles();
}
