package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.AttendanceDAO;
import com.collage.collageerp.DTO.AttendanceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceUtil {
  public List<AttendanceDTO> attendanceDaoToDtoConversion(List<AttendanceDAO> attendanceDAOS){
    List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
    for(AttendanceDAO attendanceDAO : attendanceDAOS){
      AttendanceDTO attendanceDTO = new AttendanceDTO();

      attendanceDTO.setAttendanceId(attendanceDAO.getAttendanceId());
      attendanceDTO.setDate(attendanceDAO.getDate());
      attendanceDTO.setRole(attendanceDAO.getRole());
      attendanceDTO.setUserId(attendanceDAO.getUserId());
      attendanceDTO.setStatus(attendanceDAO.isStatus());

      attendanceDTOS.add(attendanceDTO);
    }
    return attendanceDTOS;
  }

  public List<AttendanceDAO> attendanceDtoToDaoConversion(List<AttendanceDTO> attendanceDTOS){
    List<AttendanceDAO> attendanceDAOS = new ArrayList<>();
    for(AttendanceDTO attendanceDTO : attendanceDTOS){
      AttendanceDAO attendanceDAO = new AttendanceDAO();

      attendanceDAO.setAttendanceId(attendanceDTO.getAttendanceId());
      attendanceDAO.setDate(attendanceDTO.getDate());
      attendanceDAO.setRole(attendanceDTO.getRole());
      attendanceDAO.setUserId(attendanceDTO.getAttendanceId());
      attendanceDAO.setStatus(attendanceDAO.isStatus());

      attendanceDAOS.add(attendanceDAO);
    }
    return attendanceDAOS;
  }
}
