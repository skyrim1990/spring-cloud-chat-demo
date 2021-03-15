package com.tao.authenticateservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private User user;
  private List<Role> roles;

  public UserDetailsImpl(User user, List<Role> roles) {
    this.user = user;
    this.roles = roles;

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> auths = new ArrayList<>();
    for (Role role : roles) {
      auths.add(new SimpleGrantedAuthority(role.getName()));
    }
    return auths;
  }

  @Override
  public String getPassword() {
    return this.user.getPasswordDigest();
  }

  @Override
  public String getUsername() {
    return this.user.getPhonenumber();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.user.getActive() == 1;
  }
}
