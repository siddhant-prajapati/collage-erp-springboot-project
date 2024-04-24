package com.collage.collageerp.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Staffs")
public class StaffDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer staffId;

    private String staffName;

    private String profilePic;

    private String department;

    @Column(length = 10)
    private String mobileNo;

    private String address;

    @Column(unique = true, name = "Email")
    private String email;

    private String password;

    private String degree;

    private List<String> specialization;

    private String experience;

    private int attendance;

}
