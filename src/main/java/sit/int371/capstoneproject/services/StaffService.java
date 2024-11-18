package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.StaffDTO;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.repositories.StaffRepository;
import java.util.List;



@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ModelMapper modelMapper;


    //Method -get All staff
    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    //Methode -get staff by id
    public Staff getStaffById(Integer id){
        return staffRepository.findByStaffId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff id " + id + " not found !!!"));
    }

    //Method -create staff
    public StaffDTO createStaff(Staff staff){
        Staff addStaff = new Staff();
        addStaff.setStaffName(staff.getStaffName());
        addStaff.setAddress(staff.getAddress());
        addStaff.setEmail(staff.getEmail());
        addStaff.setPhone(staff.getPhone());
        return modelMapper.map(staffRepository.save(staff), StaffDTO.class);
    }

    //Method -update staff
    public StaffDTO updateStaff(Integer id, StaffDTO staffDTO){
        Staff exitsStaff = staffRepository.findByStaffId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + " does not exited!!!"));
        exitsStaff.setStaffName(staffDTO.getStaffName());
        exitsStaff.setAddress(staffDTO.getAddress());
        exitsStaff.setEmail(staffDTO.getEmail());
        exitsStaff.setPhone(staffDTO.getPhone());
        Staff updatedStaff = staffRepository.save(exitsStaff);
        return modelMapper.map(updatedStaff, StaffDTO.class);
    }

    //Method -delete staff
    public String deleteStaff(Integer id){
        if (staffRepository.existsByStaffId(id)){
            staffRepository.deleteByStaffId(id);
            return "Staff with ID " + id + " has been deleted successfully!";
        }else {
            throw new ResourceNotFoundException("Staff with ID " + id + " dose not exited!!!");
        }
    }
}
