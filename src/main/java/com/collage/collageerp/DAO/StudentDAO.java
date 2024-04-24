package com.collage.collageerp.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Students")
public class StudentDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer studentId;

    private String studentName;

    private String profilePic;

    private String department;

    private int semester;

    @Column(length = 10)
    private String mobileNo;

    private String address;

    @Column(unique = true, name = "Email")
    private String email;

    private String password;

    private int attendance;

    private String birthDate;

    private char gender;

}
