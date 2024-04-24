package com.collage.collageerp.Services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.model.LoginResponse;
import com.collage.collageerp.model.SignUpRequest;
import com.collage.collageerp.security.JwtDecoder;
import com.collage.collageerp.security.JwtIssuer;
import com.collage.collageerp.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @implNote Contain all implementation and steps how use authenticate
 */
@Service
@Slf4j
public class AuthService {

  @Autowired
  private JwtIssuer jwtIssuer;

  @Autowired
  private JwtDecoder jwtDecoder;

  @Autowired
  private StudentServiceImpl studentService;

  @Autowired
  private StaffServiceImpl staffService;

  @Autowired
  AuthenticationManager authenticationManager;
  public ResponseEntity<LoginResponse> attemptLogin(String email, String password, String role){
    try{

      // check value of email and password with UserPrincipal's email and encrypted password
      var authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );
      log.info("Authentication is {}",authentication);

      // set authentication to SecurityContextHolder
      SecurityContextHolder.getContext().setAuthentication(authentication);
      var principal = (UserPrincipal) authentication.getPrincipal();
      log.info("Principal is {}",principal);

      var roles = principal.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .toList();
      log.info("Role is {}", roles);
      //System.out.println(roles);

      //call issuer() to generate new token
      var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(),roles);
      log.info("Generated token is {}", token);
      var userID = principal.getUserId();
      return ResponseEntity.of(Optional.of(
        LoginResponse
          .builder()
          .userId(userID)
          .accessToken(token)
          .build()
      ));

    } catch (Exception e){
//      System.out.println("Error is \n"+e.getMessage());
      log.error("Error is \n"+e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }


  public ResponseEntity<?> myProfile(String token){
    try {
      String authToken = token.split(" ")[1];
      DecodedJWT decodedToken = this.jwtDecoder.decode(authToken);
      String role = String.valueOf(decodedToken.getClaim("a"));
      String email = String.valueOf(decodedToken.getClaim("e"));
      System.out.println(role);
      System.out.println(email);
      int startIndex = email.indexOf("\"");

      // Find the index of the end of the email address
      int endIndex = email.lastIndexOf("\"");
      String newEmail = email.substring(startIndex+1, endIndex);

      if(Objects.equals(role, "[\"student\"]")){
        StudentDTO student = this.studentService.findByEmail(newEmail).getBody();
        assert student != null;
        return ResponseEntity.of(Optional.of(student));
      }
      if(Objects.equals(role, "[\"staff\"]")){
        StaffDTO staff = this.staffService.findByEmail(newEmail).getBody();
        assert staff != null;
        return ResponseEntity.of(Optional.of(staff));
      }
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return null;
  }

  public ResponseEntity<?> signUpUser(SignUpRequest request){
    try {
      System.out.println(request.getRole());
      if(Objects.equals(request.getRole(), "staff")){
        StaffDTO staff = this.staffService.signUpStaff(request).getBody();
        assert staff != null;
        return ResponseEntity.of(Optional.of(staff));
      }
      if(Objects.equals(request.getRole(), "student")){
        StudentDTO student = this.studentService.signUpStudent(request).getBody();
        assert student != null;
        return ResponseEntity.of(Optional.of(student));
      }
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      log.error("Error ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return null;
  }
}
