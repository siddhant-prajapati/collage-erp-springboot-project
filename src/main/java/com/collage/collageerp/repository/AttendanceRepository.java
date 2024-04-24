package com.collage.collageerp.repository;

import com.collage.collageerp.DAO.AttendanceDAO;
import com.collage.collageerp.DTO.AttendanceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<AttendanceDAO, Integer> {
  AttendanceDAO findById(int id);

   AttendanceDAO save(AttendanceDAO attendanceDAO);

   List<AttendanceDAO> findAll();

  List<AttendanceDAO> findByUserId(int userId);

   List<AttendanceDAO> findByDate(String date);

  List<AttendanceDAO> findByRole(String role);

  //@Query("INSERT INTO AttendanceDAO (date, role, userId, status) VALUES (:date, :role, :userId, :status)")
  //List<AttendanceDAO> saveAllAttendances(List<AttendanceDAO> attendanceDAOS);

   List<AttendanceDAO> deleteAllByUserId(int userId);

   //void deletByAttendanceId(int attendanceId);
}
