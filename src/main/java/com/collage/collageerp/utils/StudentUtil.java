package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentUtil {
    public List<StudentDAO> studentDtoToDaoConversion(List<StudentDTO> studentDTOS){
        List<StudentDAO> studentDAOS = new ArrayList<>();
        for(StudentDTO studentDTO : studentDTOS){
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.setStudentId(studentDTO.getStudentId());
            studentDAO.setStudentName(studentDTO.getStudentName());
            studentDAO.setEmail(studentDTO.getEmail());
            studentDAO.setAddress(studentDTO.getAddress());
            studentDAO.setDepartment(studentDTO.getDepartment());
            studentDAO.setMobileNo(studentDTO.getMobileNo());
            studentDAO.setAttendance(studentDTO.getAttendance());
            studentDAO.setPassword(studentDTO.getPassword());
            studentDAO.setSemester(studentDTO.getSemester());
            studentDAO.setGender(studentDTO.getGender());
            studentDAO.setBirthDate(studentDTO.getBirthDate());
            studentDAO.setProfilePic(studentDTO.getProfilePic());
            studentDAOS.add(studentDAO);
        }
        return studentDAOS;
    }

    public List<StudentDTO> studentDaoToDtoConversion(List<StudentDAO> studentDAOS){
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(StudentDAO studentDAO : studentDAOS){

            StudentDTO studentDTO = new StudentDTO();

            studentDTO.setStudentId(studentDAO.getStudentId());
            studentDTO.setStudentName(studentDAO.getStudentName());
            studentDTO.setDepartment(studentDAO.getDepartment());
            studentDTO.setEmail(studentDAO.getEmail());
            studentDTO.setAddress(studentDAO.getAddress());
            studentDTO.setMobileNo(studentDAO.getMobileNo());
            studentDTO.setAttendance(studentDAO.getAttendance());
            studentDTO.setPassword(studentDAO.getPassword());
            studentDTO.setSemester(studentDAO.getSemester());
            studentDTO.setGender(studentDAO.getGender());
            studentDTO.setBirthDate(studentDAO.getBirthDate());
            studentDTO.setProfilePic(studentDAO.getProfilePic());

            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }
}
