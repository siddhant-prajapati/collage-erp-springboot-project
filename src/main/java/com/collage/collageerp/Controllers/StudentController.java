package com.collage.collageerp.Controllers;

import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.Services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
@CrossOrigin("*")
public class StudentController {

  @Autowired
  private StudentServiceImpl studentService;

  @GetMapping("/all")
  public ResponseEntity<List<StudentDTO>> getAll(){
    return this.studentService.getAllStudents();
  }

  @GetMapping("/get/{sid}")
  public ResponseEntity<StudentDTO> getById(@PathVariable("sid") Integer id){
    return this.studentService.getStudentById(id);
  }

  @PostMapping("/new")
  public ResponseEntity<StudentDTO> createNew(@RequestBody StudentDAO student){
    return this.studentService.createStudent(student);
  }

  @PutMapping("/update/{sid}")
  public ResponseEntity<StudentDTO> updateById(@PathVariable("sid") Integer id, @RequestBody StudentDAO student){
    return this.studentService.updateStudent(id, student);
  }

  @DeleteMapping("/delete/{sid}")
  public ResponseEntity<StudentDTO> deleteById(@PathVariable("sid") Integer id) {
    return this.studentService.deleteStudent(id);
  }

  @DeleteMapping("delete/all")
  public ResponseEntity<String> deleteAll() {
    return this.studentService.deleteAllStudents();
  }

  @GetMapping("/get-by-department/{department}")
  public ResponseEntity<List<StudentDTO>> getStudentByDepartment(@PathVariable("department") String department){
    return this.studentService.getStudentByDepartment(department);
  }

  @PatchMapping("/update-password/{email}")
  public ResponseEntity<StudentDTO> forgetStudentPassword(@PathVariable("email") String email, @RequestBody StudentDAO student){
    return this.studentService.updateStudentPassword(email, student.getPassword());
  }

}
