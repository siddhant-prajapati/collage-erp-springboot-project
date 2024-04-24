package com.collage.collageerp.Controllers;

import com.collage.collageerp.DAO.DepartmentDAO;
import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DTO.DepartmentDTO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.Services.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {

  @Autowired
  private DepartmentServiceImpl departmentService;

  @GetMapping("/all")
  public ResponseEntity<List<DepartmentDTO>> getAll(){
    return this.departmentService.findAllDepartment();
  }

  @GetMapping("/get/{sid}")
  public ResponseEntity<DepartmentDTO> getById(@PathVariable("sid") Integer id){
    return this.departmentService.findDepartmentById(id);
  }

  @PostMapping("/new")
  public ResponseEntity<DepartmentDTO> createNew(@RequestBody DepartmentDAO departmentDAO){
    return this.departmentService.createDepartment(departmentDAO);
  }

  @PutMapping("/update/{sid}")
  public ResponseEntity<DepartmentDTO> updateById(@PathVariable("sid") Integer id, @RequestBody DepartmentDAO departmentDAO){
    return this.departmentService.updateDepartment(id, departmentDAO);
  }

  @DeleteMapping("/delete/{sid}")
  public ResponseEntity<DepartmentDTO> deleteById(@PathVariable("sid") Integer id) {
    return this.departmentService.deleteDepartmentById(id);
  }

}
