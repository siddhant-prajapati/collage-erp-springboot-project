package com.collage.collageerp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @implNote JwtIssuer class is responsible to issue new jwt token
 */
@Component
public class JwtIssuer {

  private final JwtProperties jwtProperties;

  public JwtIssuer(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  /**
   * @implNote issue method generate new token using userId, email and roles
   * @param userId
   * @param email
   * @param roles
   * @return new jwtToken
   */
  public String issue(long userId, String email, List<String> roles){
    return JWT
        .create()
        //set value of userId as subject of token
        .withSubject(String.valueOf(userId))
        //set Expire time
        .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
        //set different value to different claim use can use this claim to access value that are bind to it
        .withClaim("e", email)
        .withClaim("a", roles)
        //set encryption algorithm along with key
        .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
  }
}
