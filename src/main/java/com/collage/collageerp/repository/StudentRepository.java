package com.collage.collageerp.repository;

import com.collage.collageerp.DAO.StudentDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentRepository extends CrudRepository<StudentDAO, Integer> {
    public StudentDAO findById(int id);

    public StudentDAO deleteById(int id);

    public StudentDAO save(StudentDAO studentDAO);
   public List<StudentDAO> findAllByDepartment(String department);

   public StudentDAO findByEmail(String email);
}
