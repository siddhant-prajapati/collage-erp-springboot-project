package com.collage.collageerp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
  private int examId;

  private int studentId;

  private String date;

  private String department;

  private int sem;

  private String subject;

  private int marks;
}
