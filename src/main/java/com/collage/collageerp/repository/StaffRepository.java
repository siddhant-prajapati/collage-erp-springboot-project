package com.collage.collageerp.repository;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StaffRepository extends CrudRepository<StaffDAO, Integer> {
    public StaffDAO findById(int id);

    public StaffDAO deleteById(int id);

    public StaffDAO save(StaffDAO staff);

    public List<StaffDAO> findAllByDepartment(String department);

    public StaffDAO findByEmail(String email);
}
