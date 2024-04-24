package com.collage.collageerp.Services;


import com.collage.collageerp.helper.EncoderDecoder;
import com.collage.collageerp.model.Admin;
import com.collage.collageerp.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private EncoderDecoder encoderDecoder;

  @Override
  public ResponseEntity<Admin> findAdminById(int id) {
    Admin admin;
    try {
      admin = adminRepository.findById(id);
      return ResponseEntity.of(Optional.of(admin));
    }
    catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      log.error("Error is : ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Admin> createAdmin(Admin admin) {
    Admin adminDto;
    try {
      admin.setPassword(
        encoderDecoder.encode(admin.getPassword())
      );
      adminDto = adminRepository.save(admin);
        return ResponseEntity.of(Optional.of(adminDto));
    } catch (Exception e){
      log.error("Internal Error ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Admin> updateAdmin(int id, Admin admin) {
    Admin adminDto;
    try {
      admin.setAdminId(id);
      admin.setPassword(
        encoderDecoder.encode(admin.getPassword())
      );
      adminDto = adminRepository.save(admin);
      return ResponseEntity.of(Optional.of(adminDto));
    }
    catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      log.error("Internal Error ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Admin> findByEmail(String email) {
    Admin admin;
    try {
      admin = adminRepository.findByEmail(email);
      return ResponseEntity.of(Optional.of(admin));
    }
    catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      log.error("Error is : ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
