package com.collage.collageerp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitExamDTO {
  private int studentId;
  
  private int sem;
  
  private String department;

  private List<String> subjects;

  private List<String> dates;

  private List<Integer> marks;
}
