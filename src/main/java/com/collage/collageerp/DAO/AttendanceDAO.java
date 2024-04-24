package com.collage.collageerp.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Attendance")
public class AttendanceDAO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  public Integer attendanceId;

  public Integer userId;

  public boolean status;

  public String role;

  public String date;
}
