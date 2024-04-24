package com.collage.collageerp.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.Services.AuthService;
import com.collage.collageerp.Services.StaffServiceImpl;
import com.collage.collageerp.Services.StudentServiceImpl;
import com.collage.collageerp.model.LoginRequest;
import com.collage.collageerp.model.LoginResponse;
import com.collage.collageerp.model.SignUpRequest;
import com.collage.collageerp.security.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
public class AuthController {

  @Autowired
  private AuthService authService;

  /**
   * @implNote  Controller User For Login
   * @return authService.attemptLogin(email, password)
   */
  @PostMapping("/auth/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request){
    return authService.attemptLogin(request.getEmail(), request.getPassword(), request.getRole());
  }

  /**
   * give data of current login user
   * @param token : token coming from client side
   * @return : data of current login user
   */
  @GetMapping("/my-profile")
  public ResponseEntity<?> myProfile(@RequestHeader("Authorization") String token){
    return authService.myProfile(token);
  }

  /**
   * signup user
   */
  @PostMapping("/auth/signup")
  public ResponseEntity<?> signUp(@RequestBody @Validated SignUpRequest request){
    log.info("inside signup");
    return authService.signUpUser(request);
  }
}
