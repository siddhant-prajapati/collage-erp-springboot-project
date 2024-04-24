package com.collage.collageerp.Services;

import com.collage.collageerp.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * fetch data from UserService and bind it with UserPrincipal
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final StudentServiceImpl studentService;

  private final StaffServiceImpl staffService;

  private final AdminServiceImpl adminService;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      log.info("user name is : {}", username);
      String role = findUserUsingEmail(username);
      if (Objects.equals(role, "admin")) {
        var admin = adminService.findByEmail(username).getBody();
        log.info("Admin is : {}", admin);
        assert admin != null;
        return UserPrincipal.builder()
          .userId(admin.getAdminId())
          .email(admin.getEmail())
          .authorities(List.of(new SimpleGrantedAuthority("admin")))
          .password(admin.getPassword())
          .build();
      } else if (Objects.equals(role, "staff")) {
        var staff = staffService.findByEmail(username).getBody();
        //log.info("Staff is : {}", staff);
        assert staff != null;
        return UserPrincipal.builder()
          .userId(staff.getStaffId())
          .email(staff.getEmail())
          .authorities(List.of(new SimpleGrantedAuthority("staff")))
          .password(staff.getPassword())
          .build();
      } else {
        var student = studentService.findByEmail(username).getBody();
        log.info("Student is : {}", student);
        return UserPrincipal.builder()
          .userId(student.getStudentId())
          .email(student.getEmail())
          .authorities(List.of(new SimpleGrantedAuthority("student")))
          .password(student.getPassword())
          .build();
      }
    }
      catch (NullPointerException e){
        return null;
      } catch (UsernameNotFoundException e){
        System.out.println("User not Found : "+ e.getMessage());
        return null;
      }
  }
  public String findUserUsingEmail(String email){
    String statusAdmin = adminService.findByEmail(email).getStatusCode().toString();
    String statusStaff = staffService.findByEmail(email).getStatusCode().toString();
    String statusStudent = studentService.findByEmail(email).getStatusCode().toString();
    if(Integer.parseInt(statusAdmin.substring(0,3))==200){
      System.out.println("Role is "+statusAdmin.substring(0,3));
      return "admin";
    }
    else if(Integer.parseInt(statusStaff.substring(0,3))==200){
      System.out.println("Role is "+statusStaff.substring(0,3));
      return "staff";
    }
    else {
      System.out.println("Role is "+statusStudent.substring(0,3));
      return "student";
    }
  }




}

