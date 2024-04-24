package com.collage.collageerp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @implNote UserPrincipalAuthenticationToken class is responsible for set the authentication true
 */
@Slf4j
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

  private final UserPrincipal principal;

  /**
   * @implNote set the authentication true and set data in UserPrincipal
   * @param principal : authenticated user
   */
  public UserPrincipalAuthenticationToken(UserPrincipal principal) {
    // get all Authorities from AbstractAuthenticationToken class
    super(principal.getAuthorities());
    //log.info("UserPrincipal is : {}",principal);
    this.principal = principal;
    setAuthenticated(true);
  }

  /**
   * @implNote You can set your custom credentials here
   */
  @Override
  public Object getCredentials() {
    return null;
  }

  /**
   * @implNote get the principal from database data
   * @return principal
   */
  @Override
  public UserPrincipal getPrincipal() {
    return principal;
  }
}
