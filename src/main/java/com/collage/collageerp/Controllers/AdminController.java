package com.collage.collageerp.Controllers;

import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.Services.AdminServiceImpl;
import com.collage.collageerp.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin("*")
public class AdminController {

  @Autowired
  private AdminServiceImpl adminService;

  @GetMapping("/get/{aid}")
  public ResponseEntity<Admin> getById(@PathVariable("aid") Integer id){
    return this.adminService.findAdminById(id);
  }

  @PostMapping("/new")
  public ResponseEntity<Admin> createNew(@RequestBody Admin admin){
    return this.adminService.createAdmin(admin);
  }

  @PutMapping("/update/{sid}")
  public ResponseEntity<Admin> updateById(@PathVariable("sid") Integer id, @RequestBody Admin admin){
    return this.adminService.updateAdmin(id, admin);
  }
}
