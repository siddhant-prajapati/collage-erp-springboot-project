package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.ExamDAO;
import com.collage.collageerp.DTO.ExamDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamUtil {
  public List<ExamDTO> examDaoToDtoConvertion(List<ExamDAO> examDAOS){
    List<ExamDTO> examDTOS = new ArrayList<>();
    for(ExamDAO examDAO : examDAOS){
      ExamDTO examDTO = new ExamDTO();

      examDTO.setExamId(examDAO.getExamId());
      examDTO.setDate(examDAO.getDate());
      examDTO.setSem(examDAO.getSem());
      examDTO.setMarks(examDAO.getMarks());
      examDTO.setSubject(examDAO.getSubject());
      examDTO.setDepartment(examDAO.getDepartment());
      examDTO.setStudentId(examDAO.getStudentId());

      examDTOS.add(examDTO);
    }
    return examDTOS;
  }

  public List<ExamDAO> examDtoToDaoConversion(List<ExamDTO> examDTOS){
    List<ExamDAO> examDAOS = new ArrayList<>();
    for(ExamDTO examDTO : examDTOS){
      ExamDAO examDAO = new ExamDAO();

      examDAO.setExamId(examDTO.getExamId());
      examDAO.setDate(examDTO.getDate());
      examDAO.setSem(examDTO.getSem());
      examDAO.setMarks(examDTO.getMarks());
      examDAO.setSubject(examDTO.getSubject());
      examDAO.setDepartment(examDTO.getDepartment());
      examDAO.setStudentId(examDTO.getStudentId());

      examDAOS.add(examDAO);
    }
    return examDAOS;
   }
}
