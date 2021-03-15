package com.tao.authenticateservice.security;

import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.repository.RoleRepository;
import com.tao.authenticateservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String phonenumber) throws UsernameNotFoundException {
    User user = userRepository.findUserByPhonenumber(phonenumber);
    if(user == null) {
      throw new UsernameNotFoundException("该用户不存在!");
    }
    List<Role> roles = roleRepository.getRolesById(user.getId());
    return new UserDetailsImpl(user, roles);
  }

  public UserDetails loadUserByUserId(long userId) throws UsernameNotFoundException {
    User user = userRepository.findUserById(userId);
    if(user == null) {
      throw new UsernameNotFoundException("该用户不存在!");
    }
    List<Role> roles = roleRepository.getRolesById(user.getId());
    return new UserDetailsImpl(user, roles);
  }
 
  
}
