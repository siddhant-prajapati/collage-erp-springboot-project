package com.collage.collageerp.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @implNote This is response that is generated when user successfully login
 *  you can also give data of user that is authenticated or login (Useful in authorization)
 */
@Getter
@Builder
public class LoginResponse {

  private final String accessToken;

  private final long userId;
}
