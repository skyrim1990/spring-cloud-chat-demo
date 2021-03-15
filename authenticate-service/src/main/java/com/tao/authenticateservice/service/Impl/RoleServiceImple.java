package com.tao.authenticateservice.service.Impl;

import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.payload.PageParam;
import com.tao.authenticateservice.repository.RoleRepository;
import com.tao.authenticateservice.service.RoleService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImple implements RoleService {

  @Autowired
  private RoleRepository repository;

  @Override
  public List<Role> findRoles(@Param("name") String name, @Param("pageParam") PageParam pageParam){
    return repository.findRoles(name, pageParam);
  }

  @Override
  public Role findRoleById(long id) {
    return repository.findRoleById(id);
  }

  @Override
  public int increase(long id) {
    return repository.increase(id);
  }

  @Override
  public int decrease(long id) {
    return repository.decrease(id);
  }

  @Override
  public int insertRole(Role role) {
    return repository.insertRole(role);
  }

  @Override
  public int deleteRole(long id) {
    return repository.deleteRole(id);
  }

  @Override
  public long countRoles() {
    return repository.countRoles();
  }

  @Override
  public List<Role> findAllRoles() {
    return repository.findAllRoles();
  }

  @Override
  public Role findRoleByName(String name) {
    return repository.findRoleByName(name);
  }
  
}
