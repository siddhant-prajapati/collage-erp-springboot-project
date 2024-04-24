package com.collage.collageerp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
  public Integer attendanceId;

  public Integer userId;

  public String role;

  public String date;

  public boolean status;
}
