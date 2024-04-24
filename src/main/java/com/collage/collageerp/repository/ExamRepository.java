package com.collage.collageerp.repository;

import com.collage.collageerp.DAO.ExamDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends CrudRepository<ExamDAO, Integer> {
  ExamDAO findById(int examId);

  List<ExamDAO> findAll();

  ExamDAO save(ExamDAO exam);

//  List<ExamDAO> saveAll(List<ExamDAO> examDAOS);
  List<ExamDAO> findByStudentId(int studentId);
}
