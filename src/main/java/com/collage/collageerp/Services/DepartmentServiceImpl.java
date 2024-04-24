package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.DepartmentDAO;
import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DTO.DepartmentDTO;
import com.collage.collageerp.repository.DepartmentRepository;
import com.collage.collageerp.utils.DepartmentUtil;
import com.collage.collageerp.utils.DepartmentValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentUtil departmentUtil;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentValidation departmentValidation;

    @Override
    public ResponseEntity<List<DepartmentDTO>> findAllDepartment() {
        try {
            List<DepartmentDTO> departments = this.departmentUtil.departmentDaoToDtoConversion(
                (List<DepartmentDAO>)  this.departmentRepository.findAll()
            );
            if(departments.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.of(Optional.of(departments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<DepartmentDTO> findDepartmentById(int id) {
        DepartmentDTO departmentDTO;
        try {
            departmentDTO = this.departmentUtil.departmentDaoToDtoConversion(
                List.of(this.departmentRepository.findById(id))
            ).get(0);
            if(departmentDTO==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(departmentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<DepartmentDTO> createDepartment(DepartmentDAO departmentDAO) {

        DepartmentDTO departmentDTO;
        try {
            logger.info("Inside department : {}", departmentDAO);
            if(departmentValidation.validation(departmentDAO)){
                departmentDTO = this.departmentUtil.departmentDaoToDtoConversion(List.of(departmentDAO)).get(0);
                logger.info("Department DTO : {}", departmentDTO);
                DepartmentDAO department = this.departmentRepository.save(
                    this.departmentUtil.departmentDtoToDaoConversion(List.of(departmentDTO)).get(0)
                );
                logger.info("Department DAO : {}", departmentDAO);
                return ResponseEntity.of(Optional.of(departmentDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Internal error :", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<DepartmentDTO> updateDepartment(int id, DepartmentDAO departmentDAO) {
        DepartmentDTO departmentDTO;
        try {
            if(departmentValidation.validation(departmentDAO)){
                departmentDAO.setDepartmentId(id);
                departmentDTO = this.departmentUtil.departmentDaoToDtoConversion(List.of(departmentDAO)).get(0);
                logger.info("Department DTO : {}", departmentDTO);
                DepartmentDAO department = this.departmentRepository.save(
                    this.departmentUtil.departmentDtoToDaoConversion(List.of(departmentDTO)).get(0)
                );
                logger.info("Department DAO : {}", departmentDAO);
                return ResponseEntity.of(Optional.of(departmentDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<DepartmentDTO> deleteDepartmentById(int id) {
        DepartmentDTO departmentDTO;
        try {
            this.departmentRepository.deleteById(id);
            logger.info("Successfully delete departement of id : {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error("Department not found of Id : {}",id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
