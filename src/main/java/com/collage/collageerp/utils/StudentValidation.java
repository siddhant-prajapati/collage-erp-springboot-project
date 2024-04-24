package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.StudentDAO;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StudentValidation extends Validation{

  public boolean validation(StudentDAO user) {
    StudentValidation userValidation = new StudentValidation();
    int counter = 0;
    if (userValidation.validateName(user.getStudentName())) counter++;
    if (userValidation.validateEmail(user.getEmail())) counter++;
    if(userValidation.validateMobileNumber(user.getMobileNo())) counter++;
    if( userValidation.validateAttendance(user.getAttendance())) counter++;
    //if (userValidation.validatePassword(user.getPassword()))  counter++;
    return counter == 4;
  }
}
