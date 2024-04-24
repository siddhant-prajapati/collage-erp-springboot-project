package com.collage.collageerp.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Department")
public class DepartmentDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departmentId;

    @Column(unique = true)
    private String departmentName;

    private int maxStudent;
}
