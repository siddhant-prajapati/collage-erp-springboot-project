package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.DepartmentDAO;
import com.collage.collageerp.DTO.DepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {

    ResponseEntity<List<DepartmentDTO>> findAllDepartment();

    ResponseEntity<DepartmentDTO> findDepartmentById(int id);

    ResponseEntity<DepartmentDTO> createDepartment(DepartmentDAO departmentDAO);

    ResponseEntity<DepartmentDTO> updateDepartment(int id,DepartmentDAO departmentDAO);

    ResponseEntity<DepartmentDTO> deleteDepartmentById(int id);
}
