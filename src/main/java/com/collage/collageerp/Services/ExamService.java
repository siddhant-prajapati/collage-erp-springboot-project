package com.collage.collageerp.Services;

import com.collage.collageerp.DTO.ExamDTO;
import com.collage.collageerp.DTO.SubmitExamDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamService {
  ResponseEntity<List<ExamDTO>> getAllExams();

  ResponseEntity<ExamDTO> getExamById(int id);

  ResponseEntity<ExamDTO> createExam(ExamDTO examDTO);

  ResponseEntity<ExamDTO> updateExam(int id, ExamDTO examDTO);

  ResponseEntity<?> deleteExam(int id);

  ResponseEntity<List<ExamDTO>> submitExamData(SubmitExamDTO submitExamDTO);

  ResponseEntity<List<ExamDTO>> getExamDetailsByStudentId(int studentId);

  ResponseEntity<ExamDTO> updateMarkById(int id, int mark);
}
