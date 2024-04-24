package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DTO.StaffDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StaffUtil {
    public List<StaffDAO> staffDtoToDaoConversion(List<StaffDTO> staffDTOS){
        List<StaffDAO> staffDAOS = new ArrayList<>();
        for(StaffDTO staffDTO : staffDTOS){
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setStaffId(staffDTO.getStaffId());
            staffDAO.setStaffName(staffDTO.getStaffName());
            staffDAO.setEmail(staffDTO.getEmail());
            staffDAO.setAddress(staffDTO.getAddress());
            staffDAO.setDepartment(staffDTO.getDepartment());
            staffDAO.setAttendance(staffDTO.getAttendance());
            staffDAO.setMobileNo(staffDTO.getMobileNo());
            staffDAO.setPassword(staffDTO.getPassword());
            staffDAO.setSpecialization(staffDTO.getSpecialization());
            staffDAO.setExperience(staffDTO.getExperience());
            staffDAO.setDegree(staffDTO.getDegree());
            staffDAO.setProfilePic(staffDTO.getProfilePic());
            staffDAOS.add(staffDAO);
        }
        return staffDAOS;
    }

    public List<StaffDTO> staffDaoToDtoConversion(List<StaffDAO> staffDAOS){
        List<StaffDTO> staffDTOS = new ArrayList<>();
        for(StaffDAO staffDAO : staffDAOS){
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setStaffId(staffDAO.getStaffId());
            staffDTO.setStaffName(staffDAO.getStaffName());
            staffDTO.setEmail(staffDAO.getEmail());
            staffDTO.setAddress(staffDAO.getAddress());
            staffDTO.setDepartment(staffDAO.getDepartment());
            staffDTO.setAttendance(staffDAO.getAttendance());
            staffDTO.setMobileNo(staffDAO.getMobileNo());
            staffDTO.setPassword(staffDAO.getPassword());
            staffDTO.setProfilePic(staffDAO.getProfilePic());
            staffDTO.setExperience(staffDAO.getExperience());
            staffDTO.setDegree(staffDAO.getDegree());
            staffDTO.setSpecialization(staffDAO.getSpecialization());
            staffDTOS.add(staffDTO);
        }
        return staffDTOS;
    }
}
