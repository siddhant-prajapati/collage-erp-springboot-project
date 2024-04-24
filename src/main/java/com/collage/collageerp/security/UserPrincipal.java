package com.collage.collageerp.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * This user UserPrincipal is not true user, This Entity is only use for authenticate User
 */
@Getter
@Builder
public class UserPrincipal implements UserDetails {

  private final long userId;

  private final String email;

  private final String role;

  @JsonIgnore
  private final String password;

  private final Collection<? extends GrantedAuthority> authorities;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * @implNote From here we get password when we login
   * @return password
   */
  @Override
  public String getPassword() {
    return password;
  }


  /**
   * @implNote From getUsername() we get the username while login
   * @return email
   */
  @Override
  public String getUsername() {
    return email;
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
    return true;
  }
}
