package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.helper.EncoderDecoder;
import com.collage.collageerp.model.SignUpRequest;
import com.collage.collageerp.repository.StudentRepository;
import com.collage.collageerp.utils.StudentUtil;
import com.collage.collageerp.utils.StudentValidation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentUtil studentUtil;

    @Autowired
    private StudentValidation studentValidation;

    @Autowired
    private EncoderDecoder encoderDecoder;

    /**
     * Get all students
     * @return list of students
     */
    @Override
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try {
            List<StudentDAO> students =(List<StudentDAO>) this.studentRepository.findAll();
            if(students.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.of(Optional.of(this.studentUtil.studentDaoToDtoConversion(students)));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * get data of student based of id
     * @param id : studentId
     * @return data of student
     */
    @Override
    public ResponseEntity<StudentDTO> getStudentById(int id) {
        StudentDTO studentDTO;
        try {
            studentDTO = this.studentUtil.studentDaoToDtoConversion(
                List.of(this.studentRepository.findById(id))
            ).get(0);
            return ResponseEntity.of(Optional.of(studentDTO));
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            log.error("Error is : ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudentDTO> getStudentProfile(String token) {
        return null;
    }

    /**
     * create new student
     * @param student : request from client
     * @return : data of new created student
     */
    @Override
    public ResponseEntity<StudentDTO> createStudent(StudentDAO student) {
        StudentDTO studentDTO;
        try {
            logger.info("Student Data : {}", student);
            if(studentValidation.validation(student)){
                student.setPassword(
                    encoderDecoder.encode(student.getPassword())
                );
                log.info("Student is {}",student);
                studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(student)).get(0);
                logger.info("Student DTO : {}", studentDTO);
                StudentDAO studentDAO = this.studentRepository.save(
                    this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
                );
                logger.info("Student DAO : {}", studentDAO);
                return ResponseEntity.of(Optional.of(studentDTO));
            } else {
                logger.error("Validation Fail");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            logger.error("Internal Error ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update of student based on studentId
     * @param id : studentId
     * @param student : new data that to be update
     * @return updated student
     */
    @Override
    public ResponseEntity<StudentDTO> updateStudent(int id, StudentDAO student) {
        StudentDTO studentDTO;
        try {
            if(studentValidation.validation(student)){
                student.setStudentId(id);
                student.setPassword(
                    encoderDecoder.encode(student.getPassword())
                );
                studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(student)).get(0);
                logger.info("Student DTO : {}", studentDTO);
                StudentDAO studentDAO = this.studentRepository.save(
                    this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
                );
                logger.info("Student DAO : {}", studentDAO);
                return ResponseEntity.of(Optional.of(studentDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * delete student based on studentId
     * @param id : studentId
     * @return status code
     */
    @Override
    public ResponseEntity<StudentDTO> deleteStudent(int id) {
        logger.info("Id is : {}", id);
        StudentDTO studentDTO;
        try {
            StudentDAO student = this.studentRepository.deleteById(id);
            logger.info("Student DAO : {}", student);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            logger.error("Internal Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * delete all students from database
     * @return : string message or status code
     */
    @Override
    public ResponseEntity<String> deleteAllStudents() {
        try {
            this.studentRepository.deleteAll();
            return ResponseEntity.of(Optional.of("Successfully delete all Students"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * get data of students based on department
     * @param department : name of department
     * @return : list of students in any department
     */
    @Override
    public ResponseEntity<List<StudentDTO>> getStudentByDepartment(String department) {
        try {
            List<StudentDTO> students = this.studentUtil.studentDaoToDtoConversion(
                (List<StudentDAO>) studentRepository.findAllByDepartment(department)
            );
            if(students.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.of(Optional.of(students));
        }

        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * find student based on email
     * @param email : student email
     * @return - student : object
     */
    @Override
    public ResponseEntity<StudentDTO> findByEmail(String email) {
        try {
            StudentDAO student = studentRepository.findByEmail(email);
            StudentDTO studentDTO = studentUtil.studentDaoToDtoConversion(List.of(student)).get(0);
            if(studentDTO == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(studentDTO));
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (HttpServerErrorException.InternalServerError e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * update data of student based on email
     * @param email : student email
     * @param password : password as a string
     * @return : updated student data
     */
    @Override
    public ResponseEntity<StudentDTO> updateStudentPassword(String email, String password){
        StudentDTO studentDTO;
        try {
            StudentDAO oldStudent = this.studentRepository.findByEmail(email);
            //student.setEmail(email);
            oldStudent.setPassword(
                encoderDecoder.encode(password)
            );
            studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(oldStudent)).get(0);
            logger.info("Student DTO : {}", studentDTO);
            StudentDAO studentDAO = this.studentRepository.save(
                this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
            );
            logger.info("Student DAO : {}", studentDAO);
            return ResponseEntity.of(Optional.of(studentDTO));

        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * used to fill attendance of students
     * @param id : studentId
     * @return : student with incremented attendance
     */
    @Override
    public ResponseEntity<StudentDTO> fillStudentAttendance(int id) {
        StudentDTO studentDTO;
        try {
            StudentDAO oldStudent = this.studentRepository.findById(id);
            oldStudent.setAttendance(oldStudent.getAttendance() + 1);
            studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(oldStudent)).get(0);
            logger.info("Student DTO : {}", studentDTO);
            StudentDAO studentDAO = this.studentRepository.save(
                this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
            );
            logger.info("Student DAO : {}", studentDAO);
            return ResponseEntity.of(Optional.of(studentDTO));
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * SignUp student based signup request
     * @param request : student request
     * @return : updated student data
     */
    @Override
    public ResponseEntity<StudentDTO> signUpStudent(SignUpRequest request) {
        StudentDTO studentDTO;
        try {
            StudentDAO oldStudent = this.studentRepository.findByEmail(request.getEmail());
            //student.setEmail(email);
            oldStudent.setPassword(
                encoderDecoder.encode(request.getPassword())
            );
            oldStudent.setStudentName(request.getUsername());

            studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(oldStudent)).get(0);
            logger.info("Student DTO : {}", studentDTO);
            StudentDAO studentDAO = this.studentRepository.save(
                this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
            );
            logger.info("Student DAO : {}", studentDAO);
            return ResponseEntity.of(Optional.of(studentDTO));

        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * set profilePic field of student
     * @param id
     * @param imageName
     * @return
     */
    @Override
    public ResponseEntity<StudentDTO> setStudentProfilePic(int id, String imageName){
        StudentDTO studentDTO;
        try {
            StudentDAO student = this.studentRepository.findById(id);
            //student.setEmail(email);
            student.setProfilePic(imageName);
            studentDTO = this.studentUtil.studentDaoToDtoConversion(List.of(student)).get(0);
            //logger.info("Student DTO : {}", staffDTO);
            StudentDAO studentDAO = this.studentRepository.save(
                this.studentUtil.studentDtoToDaoConversion(List.of(studentDTO)).get(0)
            );
            //logger.info("Student DAO : {}", staffDAO);
            return ResponseEntity.of(Optional.of(studentDTO));

        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
