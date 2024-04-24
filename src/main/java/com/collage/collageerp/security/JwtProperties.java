package com.collage.collageerp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @implNote JwtProperty is used to get secret key from application.properties file and set it to secretKey variable
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
  private String secretKey;
}
