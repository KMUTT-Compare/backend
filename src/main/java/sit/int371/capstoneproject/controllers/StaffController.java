package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateStaffService;
import sit.int371.capstoneproject.dtos.StaffDTO;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.services.StaffService;

import java.util.List;



@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://127.0.0.1:5173/","https://cp24kk2.sit.kmutt.ac.th/"})
@RequestMapping("/api/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    @Autowired
    private SequenceGenerateStaffService sequenceGenerateStaffService;

    //Get All Staff
    @GetMapping("")
    public List<StaffDTO> getAllStaffDTO(){
        List<Staff> staffList = staffService.getAllStaff();
        return listMapper.mapList(staffList, StaffDTO.class, modelMapper);
    }

    // Get Staff By id
    @GetMapping("/{id}")
    public StaffDTO getStaffDTO(@PathVariable Integer id) {
        return modelMapper.map(staffService.getStaffById(id), StaffDTO.class);
    }

    // Create Staff
    @PostMapping("")
    public StaffDTO createdStaff(@Valid @RequestBody StaffDTO staffDTO ){
        //generate staff id
        staffDTO.setStaffId((int) sequenceGenerateStaffService.generateSequence(Staff.SEQUENCE_NAME));
        Staff staff = modelMapper.map(staffDTO, Staff.class);
        return staffService.createStaff(staff);

    }

    // Update Staff
    @PutMapping("/{id}")
    public StaffDTO updatedStaff(@PathVariable Integer id, @Valid @RequestBody StaffDTO staffDTO){
        //generate staff id
        staffDTO.setStaffId((int) sequenceGenerateStaffService.generateSequence(Staff.SEQUENCE_NAME));
        return staffService.updateStaff(id, staffDTO);
    }

    // Delete Staff
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedStaff(@PathVariable Integer id){
        String message = staffService.deleteStaff(id); // รับข้อความยืนยันจาก service
        return ResponseEntity.ok(message); // ส่งข้อความกลับไปเป็น response
    }
}
