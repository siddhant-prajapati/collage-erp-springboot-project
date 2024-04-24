package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.model.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<List<StudentDTO>> getAllStudents();

    ResponseEntity<StudentDTO> getStudentById(int id);

    ResponseEntity<StudentDTO> getStudentProfile(String token);

    ResponseEntity<StudentDTO> createStudent(StudentDAO student);

    ResponseEntity<StudentDTO> updateStudent(int id, StudentDAO student);

    ResponseEntity<StudentDTO> deleteStudent(int id);

    ResponseEntity<String> deleteAllStudents();

    ResponseEntity<StudentDTO> updateStudentPassword(String email, String password);

    ResponseEntity<StudentDTO> fillStudentAttendance(int id);

    ResponseEntity<StudentDTO> signUpStudent(SignUpRequest request);

    ResponseEntity<StudentDTO> setStudentProfilePic(int id, String imageName);

    ResponseEntity<StudentDTO> findByEmail(String email);

    ResponseEntity<List<StudentDTO>> getStudentByDepartment(String department);
}
