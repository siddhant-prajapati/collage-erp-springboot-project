package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.DepartmentDAO;
import com.collage.collageerp.DTO.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DepartmentUtil {
    public List<DepartmentDTO> departmentDaoToDtoConversion(List<DepartmentDAO> departmentDAOS){
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        for(DepartmentDAO departmentDAO : departmentDAOS){

            DepartmentDTO departmentDTO = new DepartmentDTO();

            departmentDTO.setDepartmentId(departmentDAO.getDepartmentId());
            departmentDTO.setDepartmentName(departmentDAO.getDepartmentName());
            departmentDTO.setMaxStudent(departmentDAO.getMaxStudent());

            departmentDTOS.add(departmentDTO);
        }
        log.info("Inside department dao to dto");
        return departmentDTOS;
    }

    public List<DepartmentDAO> departmentDtoToDaoConversion(List<DepartmentDTO> departmentDTOS){
        List<DepartmentDAO> departmentDAOS = new ArrayList<>();
        for(DepartmentDTO departmentDTO : departmentDTOS){
            DepartmentDAO departmentDAO = new DepartmentDAO();

            departmentDAO.setDepartmentId(departmentDTO.getDepartmentId());
            departmentDAO.setDepartmentName(departmentDTO.getDepartmentName());
            departmentDAO.setMaxStudent(departmentDTO.getMaxStudent());

            departmentDAOS.add(departmentDAO);
        }
        return departmentDAOS;
    }
}
