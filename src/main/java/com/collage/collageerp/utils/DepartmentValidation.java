package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DepartmentValidation extends Validation{

  private static final Logger logger = LoggerFactory.getLogger(DepartmentValidation.class);
  private boolean validateMaxStudent(int studentNum) {
    try {
      if(studentNum<0 || studentNum > 1000) {
        throw new Exception("Enter valid Student Strength");
      }
    } catch (Exception e){
      logger.error("Enter valid Student Strength", e);
      return false;
    }
    return true;
  }

  public boolean validation(DepartmentDAO department) {
    DepartmentValidation departmentValidation = new DepartmentValidation();
    int counter = 0;
    if (departmentValidation.validateDepartment(department.getDepartmentName())) counter++;
    if (departmentValidation.validateMaxStudent(department.getMaxStudent())) counter++;
    return counter == 2;
  }
}
