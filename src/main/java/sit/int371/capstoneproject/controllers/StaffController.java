package sit.int371.capstoneproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.dtos.StaffDTO;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.services.StaffService;

import java.util.List;


@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get Staff All Staff
    @GetMapping("")
    public List<StaffDTO> getAllStaffDTO() {
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
//    public StaffDTO createStaff(@RequestBody StaffDTO staffDTO ){
//        Staff staff = modelMapper.map(staffDTO, Staff.class);
//        return staffService.createStaff(staff);
//    }

    public StaffDTO createAnnouncement(@RequestBody StaffDTO staffDTO ){
        Staff staff = modelMapper.map(staffDTO,Staff.class);
        return staffService.createStaff(staff);
    }
}
