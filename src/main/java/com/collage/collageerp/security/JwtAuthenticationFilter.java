package com.collage.collageerp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JwtAuthentication only use when we hit the secure url
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtDecoder jwtDecoder;
  private final JwtToPrincipalConverter jwtToPrincipalConverter;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
        "Accept-Encoding, origin, content-type, accept, token, x-auth-token, Access-Control-Allow-Origin, " +
            "Access-Control-Allow-Methods, Access-Control-Max-Age, Access-Control-Allow-Headers, " +
            "Content-Language, Content-Length, Keep-Alive, Authorization");

    //System.out.println("Request is = " + response.getHeader("Authorization"));
    log.info("Response is = " + response);


    extractTokenFromRequest(request)
        .map(jwtDecoder::decode)   // same as .map(str -> jwtDecoder.decode())
        .map(jwtToPrincipalConverter::convert) // calling convert() method
        .map(UserPrincipalAuthenticationToken::new) //generate new object of class UserPrincipalAuthenticationToken
        .ifPresent(     // check if token is present or not
            authentication -> SecurityContextHolder
              .getContext()
              .setAuthentication(authentication)
        );

    filterChain.doFilter(request, response); //implement filter
  }

  /**
   * @implNote extractTokenFromRequest method take request and extract token from request
   * @param request
   * @return token if found or empty
   */
  private Optional<String> extractTokenFromRequest(HttpServletRequest request){
    var token = request.getHeader("Authorization"); // get "Authorization field from header

    //System.out.println("Token from header " + token);
    log.info("Token from header " + token);
    log.info("User principal is : {}",request.getUserPrincipal());
    if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
      return Optional.of(token.substring(7));
    }
    return Optional.empty();
  }

}
