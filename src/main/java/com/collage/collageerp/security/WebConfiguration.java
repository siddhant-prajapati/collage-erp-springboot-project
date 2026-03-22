package com.collage.collageerp.security;

import com.collage.collageerp.Services.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  private final CustomUserDetailService customUserDetailService;

  private final UnauthorizeHandler unauthorizeHandler;
  /**
   *
   * @param http
   * @apiNote This security filter is use before executing the code
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception{

    // this filter apply before jwtAuthentication filter
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http
        //disable cors(cross origin resource sharing) so we can easily use this api with other application
         .cors(cors -> cors.disable())
        //we can use this api in postman instead of fill form in browser
        .csrf(csrf -> csrf.disable())
        //manage session as stateless
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        //disable from that is appeared in browser
        .formLogin().disable()
        .exceptionHandling()
          .authenticationEntryPoint(unauthorizeHandler)
        .and()
        .securityMatcher("/**") //apply this security of given pattern
        .authorizeHttpRequests(registry -> registry
            // permitAll() url path free from security
            .requestMatchers(
                "/student/update-password/**",
                "/student/new",
                "/staff/all",
                "/student/get/**",
                "/student/delete/**",
                "/staff/update-password/**",
                "/auth/login",
                "/auth/signup",
                "/attendance/**",
                "/my-profile",
                "/image/fileSystem/**",
                "/student/get-by-department/**",
                "/staff/get-by-department/**",
                "admin/**"
            ).permitAll()
//            .requestMatchers("/student/new").permitAll()
//            .requestMatchers("/staff/new").permitAll()
//            .requestMatchers("/admin/**").hasRole("ADMIN")
            // any request except permitAll() should be authenticated
            .anyRequest().authenticated()
        );
    return http.build();
  }

  /**
   * @implNote This method is used to encode password
   * @return Encrypted password with Bcrypt
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * @implNote authenticationManager method use to authenticate request
   * @param http :- response from user
   * @throws Exception :- wrong password exception
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    //log.info("Http request is : {}", http.getSharedObjects());
    return http
        .getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(customUserDetailService)
        .passwordEncoder(passwordEncoder())
        .and().build();

  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
      }
    };
  }
}
