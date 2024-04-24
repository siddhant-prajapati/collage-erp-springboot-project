package com.collage.collageerp.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @implNote this class take decoded data of token and convert it to principal
 */
@Component
public class JwtToPrincipalConverter {

  /**
   * @implNote Convert get all value of jwt token and set it to user principal
   * @param jwt : it is decoded jwt token
   * @return UserPrincipal : authenticated user
   */
  public UserPrincipal convert(DecodedJWT jwt){
    return UserPrincipal.builder()
        .userId(Long.valueOf(jwt.getSubject()))
        .email(jwt.getClaim("e").asString())
        .authorities(extractAuthoritiesFromClaim(jwt))
        .build();
  }

  /**
   * @implNote this method get authorities of jwt claim
   * @param jwt : it is decoded jwt token
   * @return list of all authorities
   */
  private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
    var claim = jwt.getClaim("a");
    if ( claim.isNull() || claim.isMissing()) return List.of();
    return claim.asList(SimpleGrantedAuthority.class);
  }
}
