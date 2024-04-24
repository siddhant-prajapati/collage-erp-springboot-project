package com.collage.collageerp.Controllers;

import com.collage.collageerp.DTO.ExamDTO;
import com.collage.collageerp.DTO.SubmitExamDTO;
import com.collage.collageerp.Services.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("exam")
@CrossOrigin("*")
public class ExamController {

  @Autowired
  private ExamServiceImpl examService;

  @GetMapping("/all")
  public ResponseEntity<List<ExamDTO>> getAll(){
    return this.examService.getAllExams();
  }

  @GetMapping("/get/{eid}")
  public ResponseEntity<ExamDTO> getById(@PathVariable("eid") Integer id){
    return this.examService.getExamById(id);
  }

  @PostMapping("/new")
  public ResponseEntity<ExamDTO> createNew(@RequestBody ExamDTO exams){
    return this.examService.createExam(exams);
  }

  @PutMapping("/update/{eid}")
  public ResponseEntity<ExamDTO> updateById(@PathVariable("eid") Integer id, @RequestBody ExamDTO examDTO){
    return this.examService.updateExam(id, examDTO);
  }

  @DeleteMapping("/delete/{eid}")
  public ResponseEntity<?> deleteById(@PathVariable("eid") Integer id) {
    return this.examService.deleteExam(id);
  }

  @PostMapping("submit-data")
  public ResponseEntity<List<ExamDTO>> fillExamData(@RequestBody SubmitExamDTO submitExamDTO){
    System.out.println(submitExamDTO);
    return this.examService.submitExamData(submitExamDTO);
  }

  @GetMapping("/get-by-studentId/{sId}")
  public ResponseEntity<List<ExamDTO>> getExamsByStudentId(@PathVariable("sId") Integer studentId){
    return this.examService.getExamDetailsByStudentId(studentId);
  }

  @GetMapping("/update-mark/{eId}")
  public ResponseEntity<ExamDTO> updateMarkById(@PathVariable("eId") Integer examId, @RequestParam("mark") Integer mark){
    return this.examService.updateMarkById(examId, mark);
  }
}
