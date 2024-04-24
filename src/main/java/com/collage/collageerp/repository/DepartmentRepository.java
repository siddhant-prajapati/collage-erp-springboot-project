package com.collage.collageerp.repository;

import com.collage.collageerp.DAO.DepartmentDAO;
import com.collage.collageerp.DAO.StudentDAO;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<DepartmentDAO, Integer> {
    public DepartmentDAO findById(int id);

    public DepartmentDAO deleteById(int id);

    public DepartmentDAO save(DepartmentDAO department);
}
