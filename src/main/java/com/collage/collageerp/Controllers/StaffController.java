package com.collage.collageerp.Controllers;

import com.collage.collageerp.DAO.StaffDAO;
import com.collage.collageerp.DAO.StudentDAO;
import com.collage.collageerp.DTO.StaffDTO;
import com.collage.collageerp.DTO.StudentDTO;
import com.collage.collageerp.Services.StaffServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("staff")
@CrossOrigin("http://localhost:4200")
public class StaffController {

  @Autowired
  private StaffServiceImpl staffService;

  @GetMapping("/all")
  public ResponseEntity<List<StaffDTO>> getAll(){
    return this.staffService.getAllStaffs();
  }

  /**
   * getting data of staff using Id
   * @param id : staffId
   * @return StaffDTO
   */
  @GetMapping("/get/{sid}")
  public ResponseEntity<StaffDTO> getById(@PathVariable("sid") Integer id){
    return this.staffService.getStaffById(id);
  }

  /**
   * Create new Staff
   * @param staff : Request data for Staff
   * @return new created Staff
   */
  @PostMapping("/new")
  public ResponseEntity<StaffDTO> createNew(@RequestBody StaffDAO staff){
    System.out.println(staff);
    return this.staffService.createStaff(staff);
  }

  /**
   * Replace new Data in place of old staff data
   * @param id : staffId
   * @param staff : NewData of Staff
   * @return Updated data of Staff
   */
  @PutMapping("/update/{sid}")
  public ResponseEntity<StaffDTO> updateById(@PathVariable("sid") Integer id, @RequestBody StaffDAO staff){
    return this.staffService.updateStaff(id, staff);
  }

  /**
   * delete Staff using StaffId
   * @param id : StaffId
   * @return httpStatus
   */
  @CrossOrigin("*")
  @DeleteMapping("/delete/{sid}")
  public ResponseEntity<StaffDTO> deleteById(@PathVariable("sid") Integer id) {
    log.info("Staff id = {}",id);
    return this.staffService.deleteStaff(id);
  }

  /**
   * delete all data of staff
   * @return message for successfully delete
   */
  @DeleteMapping("delete/all")
  public ResponseEntity<String> deleteAll() {
    return this.staffService.deleteAllStaffs();
  }

  /**
   * give list of staff using department
   * @param department : name of Department
   * @return : list of staff
   */
  @GetMapping("/get-by-department/{department}")
  public ResponseEntity<List<StaffDTO>> getByDepartment(@PathVariable("department") String department){
    return this.staffService.getStaffByDepartment(department);
  }

  @PatchMapping("/update-password/{email}")
  public ResponseEntity<StaffDTO> forgetStaffPassword(@PathVariable("email") String email, @RequestBody StaffDAO staff){
    return this.staffService.updateStaffPassword(email, staff.getPassword());
  }

  @GetMapping("/get-by-mail/{email}")
  public ResponseEntity<StaffDTO> findByEmail(@PathVariable("email") String email){
    return this.staffService.findByEmail(email);
  }


}
