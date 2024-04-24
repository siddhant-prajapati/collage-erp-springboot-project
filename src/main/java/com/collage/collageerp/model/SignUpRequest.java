package com.collage.collageerp.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {
  private String username;
  private String email;
  private String password;
  private String role;
}
