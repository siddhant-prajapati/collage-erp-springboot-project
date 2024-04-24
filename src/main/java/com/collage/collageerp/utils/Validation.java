package com.collage.collageerp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

  private static final Logger logger = LoggerFactory.getLogger(Validation.class);
  // Use for validating any kind of name
  protected boolean validateName(String userName) {
    try {
      if (userName.isEmpty()) {
        throw new Exception("User Name must not empty");
      }
    } catch (Exception e) {
      logger.error("User Name must not empty");
      return false;
    }
    return true;
  }

  protected boolean validateEmail(String email) {
    try {
      String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(email);
      if (!m.matches()) {
        throw new Exception("Enter valid Email");
      }

    } catch (Exception e) {
      logger.error("Enter valid Email", e);
      return false;
    }
    return true;
  }

  protected boolean validatePassword(String password) {
    //        password must follow below all conditions
    //        ^ represents starting character of the string.
    //        (?=.*[0-9]) represents a digit must occur at least once.
    //        (?=.*[a-z]) represents a lower case alphabet must occur at least once.
    //        (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
    //        (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
    //        (?=\\S+$) white spaces don’t allowed in the entire string.
    //        {8, 20} represents at least 8 characters and at most 20 characters.
    //        $ represents the end of the string.
    String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[@#$%^&+=])"
        + "(?=\\S+$).{8,20}$";

    try {
      if (password.length() < 8) {
        logger.error("Password's length must be greater than 8");
        throw new Exception("Password's length must be greater than 8");
      }

      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(password);

      // Return if the password
      // matched the ReGex
      if (!m.matches()) {
        throw new Exception("Enter valid Password");
      }
    } catch (Exception e) {
      logger.error("Enter valid Password : {}", password, e);
      return false;
    }
    return true;
  }

  protected boolean validateMobileNumber(String mobileNumber){
    try {
      if(mobileNumber.length() > 10){
        throw new Exception("Enter Mobile Number of length 10");
      }
    } catch (Exception e){
      logger.error("Enter Mobile Number of length 10", e);
      return false;
    }
    return true;
  }

  protected boolean validateAttendance(int attendance){
    try {
      if(attendance < 0 || attendance > 150) throw new Exception("Attendance is invalid");
    } catch (Exception e){
      logger.error("Attendance is invalid", e);
      return false;
    }
    return true;
  }

  protected boolean validateDepartment(String department){
    ArrayList<String> departments = new ArrayList<>(
        Arrays.asList("computer", "mechanical", "civil", "IT", "Electrical", "chemical")
    );

    for(String name : departments) {
      if(department.equalsIgnoreCase(name)) {
        return true;
      }
    }
    logger.error("Enter valid department name");
    return false;
  }
}
