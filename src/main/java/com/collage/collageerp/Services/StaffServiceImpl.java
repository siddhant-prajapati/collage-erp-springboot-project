package com.collage.collageerp.Services;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.helper.EncoderDecoder;
import com.collage.collageerp.model.SignUpRequest;
import com.collage.collageerp.repository.StaffRepository;
import com.collage.collageerp.utils.StaffUtil;
import com.collage.collageerp.utils.StaffValidation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService{

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffUtil staffUtil;

    @Autowired
    private StaffValidation staffValidation;

    @Autowired
    private EncoderDecoder encoderDecoder;

    /**
     * List of all staff
     * @return : data of all staff
     */
    @Override
    public ResponseEntity<List<StaffDTO>> getAllStaffs() {
        try {
            List<StaffDTO> staffs = this.staffUtil.staffDaoToDtoConversion(
                (List<StaffDAO>) staffRepository.findAll()
            );
            return ResponseEntity.of(Optional.of(staffs));
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * get data of staff based on staffId
     * @param id : staffId
     * @return : staff data
     */
    @Override
    public ResponseEntity<StaffDTO> getStaffById(int id) {
        StaffDTO staffDTO;
        try {
            staffDTO = this.staffUtil.staffDaoToDtoConversion(
                List.of(this.staffRepository.findById(id))
            ).get(0);
            return ResponseEntity.of(Optional.of(staffDTO));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            log.error("Error is : ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * create new staff
     * @param staff : data of request
     * @return : newly created staff
     */
    @Override
    public ResponseEntity<StaffDTO> createStaff(StaffDAO staff) {
        StaffDTO staffDTO;
        try {
            if(staffValidation.validation(staff)){
                staff.setPassword(
                    encoderDecoder.encode(staff.getPassword())
                );
                staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(staff)).get(0);
                logger.info("Staff DTO : {}", staffDTO);
                StaffDAO staffDAO = this.staffRepository.save(
                    this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
                );
                logger.info("Staff DAO : {}", staffDAO);
                return ResponseEntity.of(Optional.of(staffDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * update staff based on staffId
     * @param id : staffId
     * @param staff : request from client
     * @return : newly updated staff
     */
    @Override
    public ResponseEntity<StaffDTO> updateStaff(int id, StaffDAO staff) {
        StaffDTO staffDTO;
        try {
            if(staffValidation.validation(staff)){
                staff.setStaffId(id);
                staff.setPassword(
                    encoderDecoder.encode(staff.getPassword())
                );
                staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(staff)).get(0);
                logger.info("Staff DTO : {}", staffDTO);
                StaffDAO staffDAO = this.staffRepository.save(
                    this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
                );
                logger.info("Staff DAO : {}", staffDAO);
                return ResponseEntity.of(Optional.of(staffDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * delete data of staff based on staffId
     * @param id : staffId
     * @return : status code
     */
    @Transactional
    @Override
    public ResponseEntity<StaffDTO> deleteStaff(int id) {
        StaffDTO staffDTO;
        try {
            this.staffRepository.deleteById(id);
            logger.info("Successfully deleted staff with id : {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (NullPointerException e) {
            logger.error("Staff not found of Id : {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * delete all staff
     * @return : string message
     */
    @Override
    public ResponseEntity<String> deleteAllStaffs() {
        try {
            this.staffRepository.deleteAll();
            logger.info("Successfully deleted all staffs");
            return ResponseEntity.of(Optional.of("Successfully delete all Students"));
        } catch (Exception e){
            logger.error("Enable to delete staff", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * get list of staff based on department
     * @param department : department name
     * @return : list of staff
     */
    @Override
    public ResponseEntity<List<StaffDTO>> getStaffByDepartment(String department) {
        try {
            List<StaffDTO> staffs = this.staffUtil.staffDaoToDtoConversion(
                (List<StaffDAO>) staffRepository.findAllByDepartment(department)
            );
            if(staffs.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.of(Optional.of(staffs));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * get staff based on email
     * @param email :staff email
     * @return : staff data
     */
    @Override
    public ResponseEntity<StaffDTO> findByEmail(String email) {
        try {
            StaffDAO staff = staffRepository.findByEmail(email);
            StaffDTO staffDTO = staffUtil.staffDaoToDtoConversion(List.of(staff)).get(0);
            return ResponseEntity.of(Optional.of(staffDTO));
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * update staff password based on email
     * @param email : staff email
     * @param password : password in string
     * @return : newly updated staff
     */
    @Override
    public ResponseEntity<StaffDTO> updateStaffPassword(String email, String password) {
        StaffDTO staffDTO;
        try {
            StaffDAO oldStaff = this.staffRepository.findByEmail(email);
            //student.setEmail(email);
            oldStaff.setPassword(
                encoderDecoder.encode(password)
            );
            staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(oldStaff)).get(0);
            //logger.info("Student DTO : {}", staffDTO);
            StaffDAO staffDAO = this.staffRepository.save(
                this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
            );
            //logger.info("Student DAO : {}", staffDAO);
            return ResponseEntity.of(Optional.of(staffDTO));

        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            log.error("Error in password : ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * fill staff attendance based on userId
     * @param userId : staff id
     * @return : data of staff with incremented attendance
     */
    @Override
    public ResponseEntity<StaffDTO> fillStaffAttendance(int userId) {
        StaffDTO staffDTO;
        try {
            StaffDAO oldStaff = this.staffRepository.findById(userId);
            oldStaff.setAttendance(oldStaff.getAttendance() + 1);
            staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(oldStaff)).get(0);
            this.staffRepository.save(
                this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
            );
            return ResponseEntity.of(Optional.of(staffDTO));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * signup user based on request
     * @param request : signUp request
     * @return : data of signUp user
     */
    @Override
    public ResponseEntity<StaffDTO> signUpStaff(SignUpRequest request) {
        StaffDTO staffDTO;
        try {
            StaffDAO oldStaff = this.staffRepository.findByEmail(request.getEmail());
            //student.setEmail(email);
            oldStaff.setPassword(
              encoderDecoder.encode(request.getPassword())
            );
            oldStaff.setStaffName(request.getUsername());
            staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(oldStaff)).get(0);
            //logger.info("Student DTO : {}", staffDTO);
            StaffDAO staffDAO = this.staffRepository.save(
              this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
            );
            //logger.info("Student DAO : {}", staffDAO);
            return ResponseEntity.of(Optional.of(staffDTO));
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * update staff profilePic field
     * @param id : staffId
     * @param imageName : name of image in string
     * @return : staff data with updated profilePic
     */
    @Override
    public ResponseEntity<StaffDTO> setStaffProfilePic(int id, String imageName){
        log.info(imageName);
        StaffDTO staffDTO;
        try {
            StaffDAO staff = this.staffRepository.findById(id);
            //student.setEmail(email);
            staff.setProfilePic(imageName);
            staffDTO = this.staffUtil.staffDaoToDtoConversion(List.of(staff)).get(0);
            //logger.info("Student DTO : {}", staffDTO);
            StaffDAO staffDAO = this.staffRepository.save(
                this.staffUtil.staffDtoToDaoConversion(List.of(staffDTO)).get(0)
            );
            //logger.info("Student DAO : {}", staffDAO);
            return ResponseEntity.of(Optional.of(staffDTO));

        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
