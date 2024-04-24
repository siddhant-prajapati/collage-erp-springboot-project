package com.collage.collageerp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @implNote This class is use for decode token and get all information from token
 */
@Component
@RequiredArgsConstructor
public class JwtDecoder {
  private final JwtProperties jwtProperties;

  /**
   * @implNote decode method is used to decode the token and verify the token weather it is true or not
   * @param token : generated token
   */
  public DecodedJWT decode(String token) {
    //System.out.println("Token is "+ token);
    return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
        .build()
        .verify(token);
  }
}
