package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.model.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StaffService {
    ResponseEntity<List<StaffDTO>> getAllStaffs();
    ResponseEntity<StaffDTO> getStaffById(int id);
    ResponseEntity<StaffDTO> createStaff(StaffDAO student);
    ResponseEntity<StaffDTO> updateStaff(int id, StaffDAO student);

    ResponseEntity<StaffDTO> deleteStaff(int id);

    ResponseEntity<String> deleteAllStaffs();

    ResponseEntity<StaffDTO> updateStaffPassword(String email, String password);

    ResponseEntity<StaffDTO> fillStaffAttendance(int userId);

    ResponseEntity<StaffDTO> signUpStaff(SignUpRequest request);

    ResponseEntity<StaffDTO> setStaffProfilePic(int id, String imageName);

    ResponseEntity<StaffDTO> findByEmail(String email);

    ResponseEntity<List<StaffDTO>> getStaffByDepartment(String department);
}
