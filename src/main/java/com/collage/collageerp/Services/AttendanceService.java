package com.collage.collageerp.Services;

import com.collage.collageerp.DTO.AttendanceDTO;
import com.collage.collageerp.DTO.FillAttendanceDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {
  ResponseEntity<List<AttendanceDTO>> getAllAttendance();

  ResponseEntity<List<AttendanceDTO>> getAttendanceByUserId(int userId);

  ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(String date);

  ResponseEntity<List<AttendanceDTO>> getAttendanceByRole(String role);

  ResponseEntity<List<AttendanceDTO>> fillUserAttendanceByDate(FillAttendanceDTO attendance);

  ResponseEntity<List<AttendanceDTO>> deleteStudentAttendanceByUserId(int userId);
}
