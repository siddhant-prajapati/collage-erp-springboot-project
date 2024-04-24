package com.collage.collageerp.Controllers;

import com.collage.collageerp.DTO.AttendanceDTO;
import com.collage.collageerp.DTO.FillAttendanceDTO;
import com.collage.collageerp.Services.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attendance")
@CrossOrigin("*")
public class AttendanceController {

  @Autowired
  private AttendanceServiceImpl attendanceService;

  //show all attendance
  @GetMapping("/all")
  public ResponseEntity<List<AttendanceDTO>> getAllAttendance(){
    return this.attendanceService.getAllAttendance();
  }

  //show attendance of any user
  @GetMapping("/get-by-user/{userId}")
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByUserId(@PathVariable("userId") int userId){
    return this.attendanceService.getAttendanceByUserId(userId);
  }

  //show attendance by date
  @GetMapping("/get-by-date/{date}")
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(@PathVariable("date") String date){
    return this.attendanceService.getAttendanceByDate(date);
  }

  //show attendance by role
  @GetMapping("/get-by-role/{role}")
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByRole(@PathVariable("role") String role){
    return this.attendanceService.getAttendanceByRole(role);
  }

  //fill attendance of user
  @PostMapping("/fill")
  public ResponseEntity<List<AttendanceDTO>> fillUserAttendance(@RequestBody FillAttendanceDTO attendance){
    return this.attendanceService.fillUserAttendanceByDate(attendance);
  }

  //delete attendance by userid
  @DeleteMapping("/delete-student-userId/{userId}")
  public ResponseEntity<?> deleteAttendanceOfStudent(@PathVariable("userId") int userId){
    return this.attendanceService.deleteStudentAttendanceByUserId(userId);
  }

  @DeleteMapping("/delete/{aId}")
  public ResponseEntity<?> deleteById(@PathVariable("aId") int attendanceId){
    return this.attendanceService.deleteAttendanceById(attendanceId);
  }
}
