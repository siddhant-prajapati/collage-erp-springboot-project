package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.ExamDAO;
import com.collage.collageerp.DTO.ExamDTO;
import com.collage.collageerp.DTO.SubmitExamDTO;
import com.collage.collageerp.repository.ExamRepository;
import com.collage.collageerp.utils.ExamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService{

  @Autowired
  private ExamUtil examUtil;

  @Autowired
  private ExamRepository examRepository;

  @Override
  public ResponseEntity<List<ExamDTO>> getAllExams() {
    List<ExamDTO> exams;
    try {
      exams = examUtil.examDaoToDtoConvertion(
        examRepository.findAll()
      );
      if(exams.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.of(Optional.of(exams));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<ExamDTO> getExamById(int id) {
    ExamDTO exam;
    try {
      exam = examUtil.examDaoToDtoConvertion(List.of(examRepository.findById(id))).get(0);
      return ResponseEntity.of(Optional.of(exam));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<ExamDTO> createExam(ExamDTO examDTO) {
    ExamDTO exam;
    try {
      ExamDAO examDAO = examUtil.examDtoToDaoConversion(List.of(examDTO)).get(0);
      exam = examUtil.examDaoToDtoConvertion(
        List.of(examRepository.save(examDAO))
      ).get(0);
      return ResponseEntity.of(Optional.of(exam));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<ExamDTO> updateExam(int id, ExamDTO examDTO) {
    ExamDTO exam;
    try {
      ExamDAO examDAO = examUtil.examDtoToDaoConversion(List.of(examDTO)).get(0);
      examDAO.setExamId(id);
      exam = examUtil.examDaoToDtoConvertion(
        List.of(examRepository.save(examDAO))
      ).get(0);
      return ResponseEntity.of(Optional.of(exam));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<?> deleteExam(int id) {
    try {
      examRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<ExamDTO>> submitExamData(SubmitExamDTO submitExamDTO) {
    try {
      List<String> subjects = submitExamDTO.getSubjects();
      List<Integer> marks = submitExamDTO.getMarks();
      List<String> dates = submitExamDTO.getDates();

      List<ExamDTO> examDTOS = new ArrayList<>();
      for(int i = 0 ; i < subjects.size() ; i++ ){
        ExamDTO examDTO = new ExamDTO();

        examDTO.setDate(dates.get(i));
        examDTO.setDepartment(submitExamDTO.getDepartment());
        examDTO.setSem(submitExamDTO.getSem());
        examDTO.setMarks(marks.get(i));
        examDTO.setSubject(subjects.get(i));
        examDTO.setStudentId(submitExamDTO.getStudentId());

        examDTOS.add(examDTO);
      }
      List<ExamDAO> examDAOS = examUtil.examDtoToDaoConversion(examDTOS);
      List<ExamDAO> exams = new ArrayList<>();
      for(ExamDAO examDAO : examDAOS){
        //ExamDTO examDTO = new ExamDTO();
        ExamDAO exam = examRepository.save(examDAO);
        exams.add(exam);
      }
      return ResponseEntity.of(
        Optional.of(
          examUtil.examDaoToDtoConvertion(exams)
        ));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<List<ExamDTO>> getExamDetailsByStudentId(int studentId) {
    List<ExamDTO> exams;
    try {
      exams = examUtil.examDaoToDtoConvertion(examRepository.findByStudentId(studentId));
      return ResponseEntity.of(Optional.of(exams));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<ExamDTO> updateMarkById(int id, int mark) {
    ExamDTO exam;
    try {
      ExamDAO examDAO = examRepository.findById(id);
      examDAO.setExamId(id);
      examDAO.setMarks(mark);
      exam = examUtil.examDaoToDtoConvertion(
        List.of(examRepository.save(examDAO))
      ).get(0);
      return ResponseEntity.of(Optional.of(exam));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch (Exception e){
      return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
