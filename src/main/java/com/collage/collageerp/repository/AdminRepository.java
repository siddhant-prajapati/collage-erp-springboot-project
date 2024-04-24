package com.collage.collageerp.repository;

import com.collage.collageerp.model.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
  Admin findById(int id);

  Admin save(Admin admin);

  List<Admin> findAll();

  Admin findByEmail(String email);
}
