package com.collage.collageerp.DTO;

import com.collage.collageerp.DAO.DepartmentDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Integer studentId;

    private String profilePic;

    private String studentName;

    private String department;

    private int semester;

    private String mobileNo;

    private String address;

    private String email;

    private String password;

    private int attendance;

    private String birthDate;

    private char gender;
}
