package com.collage.collageerp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Integer departmentId;

    private String departmentName;

    private int maxStudent;
}
