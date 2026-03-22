package com.collage.collageerp.utils;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.Services.AdminService;
import com.collage.collageerp.Services.StaffService;
import com.collage.collageerp.Services.StudentService;
import com.collage.collageerp.model.Admin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class DemoDataSetup {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AdminService adminService;

    @Value("${spring.admin.username}")
    private String adminUsername;

    @Value("${spring.admin.password}")
    private String password;

    @PostConstruct
    public void setupProject() throws IOException {
        System.out.println("Host : "+ System.getenv("DB_HOST"));
        System.out.println("Port : "+ System.getenv("DB_PORT"));
        setupAdmin();
        setupDemoStudent();
        setupDemoStaff();
    }

    public void setupAdmin() throws IOException {
        ResponseEntity<Admin> admins = adminService.findByEmail(adminUsername);
        if (!admins.getStatusCode().is2xxSuccessful()) {
            Admin admin = new Admin(1, "", adminUsername, password, "admin");
            adminService.createAdmin(admin);
            log.info("admin created successfully");
        }
    }

    public void setupDemoStudent() throws IOException {
        ResponseEntity<List<StudentDTO>> students = studentService.getAllStudents();
        if (students.getBody() == null || students.getBody().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    new ClassPathResource("demodata/studentdata/demo-student-data.json").getInputStream();

            List<StudentDAO> studentList =
                    mapper.readValue(inputStream, new TypeReference<List<StudentDAO>>() {});

            for (StudentDAO studentDAO : studentList) {
                ResponseEntity<StudentDTO> staffDAOResponseEntity = studentService.createStudent(studentDAO);
                log.info("Student : {}", Objects.requireNonNull(staffDAOResponseEntity.getBody()));
            }
        }
    }

    public void setupDemoStaff() throws IOException {
        ResponseEntity<List<StaffDTO>> staffs = staffService.getAllStaffs();
        if (staffs.getBody() == null || staffs.getBody().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    new ClassPathResource("demodata/staffdata/demo-staff-data.json").getInputStream();

            List<StaffDAO> staffList =
                    mapper.readValue(inputStream, new TypeReference<List<StaffDAO>>() {});

            for (StaffDAO staffDAO : staffList) {
                ResponseEntity<StaffDTO> staffDAOResponseEntity = staffService.createStaff(staffDAO);
                log.info("Staff : {}", Objects.requireNonNull(staffDAOResponseEntity.getBody()));
            }
        }
    }
}
