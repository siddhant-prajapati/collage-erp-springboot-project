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
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
          throws ServletException, IOException {

    // ✅ Allow preflight requests (VERY IMPORTANT)
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      filterChain.doFilter(request, response);
      return;
    }

    // Extract token
    Optional<String> tokenOpt = extractTokenFromRequest(request);

    if (tokenOpt.isPresent()) {
      try {
        var jwt = jwtDecoder.decode(tokenOpt.get());
        var principal = jwtToPrincipalConverter.convert(jwt);
        var authentication = new UserPrincipalAuthenticationToken(principal);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        log.error("JWT error: {}", e.getMessage());
      }
    }

    // ✅ Always continue filter chain
    filterChain.doFilter(request, response);
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
