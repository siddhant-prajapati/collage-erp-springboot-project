package com.collage.collageerp.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderDecoder {

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  public String encode(String message){
    return passwordEncoder.encode(message);
  }

  public boolean matches(String rawMessage, String encodedMessage){
    return passwordEncoder.matches(rawMessage, encodedMessage);
  }
}
