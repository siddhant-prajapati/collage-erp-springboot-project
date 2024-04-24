package com.collage.collageerp.DTO;

import com.collage.collageerp.DAO.DepartmentDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Integer staffId;

    private String staffName;

    private String profilePic;

    private String department;

    private String mobileNo;

    private String address;

    private String email;

    private String password;

    private String degree;

    private List<String> specialization;

    private String experience;

    private int attendance;
}
