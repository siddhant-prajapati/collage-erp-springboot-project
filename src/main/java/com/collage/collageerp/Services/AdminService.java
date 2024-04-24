package com.collage.collageerp.Services;

import com.collage.collageerp.model.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminService {

  ResponseEntity<Admin> findAdminById(int id);

  ResponseEntity<Admin> createAdmin(Admin admin);

  ResponseEntity<Admin> updateAdmin(int id, Admin admin);

  ResponseEntity<Admin> findByEmail(String email);
}
