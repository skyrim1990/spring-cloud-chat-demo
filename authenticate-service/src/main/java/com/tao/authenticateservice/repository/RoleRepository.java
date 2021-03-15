package com.tao.authenticateservice.repository;

import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.payload.PageParam;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {

  List<Role> findRoles(@Param("name") String name, @Param("page") PageParam pageParam);
  List<Role> findAllRoles();
  Role findRoleById(long id);
  Role findRoleByName(@Param("name") String name);
  int increase(long id);
  int decrease(long id);
  int insertRole(Role role);
  int deleteRole(long id);
  long countRoles();
  List<Role> getRolesById(long id);
}
