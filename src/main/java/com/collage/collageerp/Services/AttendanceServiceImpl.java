package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.AttendanceDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.AttendanceDTO;
import com.collage.collageerp.DTO.FillAttendanceDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.repository.AttendanceRepository;
import com.collage.collageerp.utils.AttendanceUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService{

  @Autowired
  private AttendanceUtil attendanceUtil;

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Autowired
  private StudentServiceImpl studentService;

  @Autowired
  private StaffServiceImpl staffService;

  @Override
  public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
    List<AttendanceDAO> attendences;
    try {
        attendences = this.attendanceRepository.findAll();
        if(attendences.isEmpty()){
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(
            Optional.of(this.attendanceUtil.attendanceDaoToDtoConversion(attendences))
        );
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByUserId(int userId) {
    List<AttendanceDAO> attendences;
    try {
      attendences = this.attendanceRepository.findByUserId(userId);
      if(attendences.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.of(
          Optional.of(this.attendanceUtil.attendanceDaoToDtoConversion(attendences))
      );
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(String date) {
    List<AttendanceDAO> attendences;
    try {
      attendences = this.attendanceRepository.findByDate(date);
      if(attendences.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.of(
          Optional.of(this.attendanceUtil.attendanceDaoToDtoConversion(attendences))
      );
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<AttendanceDTO>> getAttendanceByRole(String role) {
    List<AttendanceDAO> attendences;
    try {
      attendences = this.attendanceRepository.findByRole(role);
      if(attendences.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.of(
          Optional.of(this.attendanceUtil.attendanceDaoToDtoConversion(attendences))
      );

    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<AttendanceDTO>> fillUserAttendanceByDate(FillAttendanceDTO attendance) {
    if(attendance.getRole() == null){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    List<AttendanceDAO> attendanceDAOS = new ArrayList<>();
    try {
      for(Integer userId : attendance.getUserIds()){
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        attendanceDAO.setDate(attendance.getDate());
        attendanceDAO.setRole(attendance.getRole());
        attendanceDAO.setUserId(userId);
        if(Objects.equals(attendance.getRole(), "student")){
          studentService.fillStudentAttendance(userId);
        } else {
          staffService.fillStaffAttendance(userId);
        }
        attendanceDAO.setStatus(true);
        attendanceRepository.save(attendanceDAO);
        attendanceDAOS.add(attendanceDAO);
      }

      List<AttendanceDTO>  attendanceDTOS= attendanceUtil.attendanceDaoToDtoConversion(attendanceDAOS);
      //attendanceRepository.saveAllAttendances(attendanceUtil.attendanceDtoToDaoConversion(attendanceDTOS));
      return ResponseEntity.of(Optional.of(attendanceDTOS));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Transactional
  @Override
  public ResponseEntity<List<AttendanceDTO>> deleteStudentAttendanceByUserId(int userId) {
    try {
      List<AttendanceDAO> deletedAttendance = this.attendanceRepository.deleteAllByUserId(userId);
      log.info("Deleted Student is : {}", deletedAttendance);
      if(deletedAttendance.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      List<AttendanceDTO> attendanceDTOS = attendanceUtil.attendanceDaoToDtoConversion(deletedAttendance);
      return ResponseEntity.of(Optional.of(attendanceDTOS));
    } catch (Exception e){
      log.error("Error is :\n ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Transactional
  public ResponseEntity<?> deleteAttendanceById(int id) {
    try {
      attendanceRepository.deleteById(id);
      //logger.info("Student DAO : {}", student);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    catch (NullPointerException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      //logger.error("Internal Error: ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
