package com.collage.collageerp.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Exam")
public class ExamDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int examId;

  private int studentId;

  private String date;

  private String department;

  private int sem;

  private String subject;

  private int marks;
}
