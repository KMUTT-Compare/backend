package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.StaffDTO;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.StaffRepository;
import java.util.List;



@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ModelMapper modelMapper;


    //Method -get All staff
    public List<Staff> getAllStaff() {
            List<Staff> staffList = staffRepository.findAll();
            if(staffList.isEmpty()){
                throw new ResourceNotFoundException("No staff found!");
            }
            return staffList;
    }

    //Methode -get staff by id
    public Staff getStaffById(Integer id){
        return staffRepository.findByStaffId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff id " + id + " not found !!!"));
    }

    //Method -create staff
    public StaffDTO createStaff(Staff staff){
        Staff addStaff = new Staff();
        addStaff.setStaffId(staff.getStaffId());
        addStaff.setStaffName(staff.getStaffName());
        addStaff.setStaffAddress(staff.getStaffAddress());
        addStaff.setStaffEmail(staff.getStaffEmail());
        addStaff.setStaffPhone(staff.getStaffPhone());
        return modelMapper.map(staffRepository.save(staff), StaffDTO.class);
    }

    //Method -update staff
    public StaffDTO updateStaff(Integer id, StaffDTO staffDTO){
        Staff exitsStaff = staffRepository.findByStaffId(id).orElseThrow(
                () -> new ResourceNotFoundException("Staff id" + id + " does not exited!!!"));
        exitsStaff.setStaffName(staffDTO.getStaffName());
        exitsStaff.setStaffAddress(staffDTO.getStaffAddress());
        exitsStaff.setStaffEmail(staffDTO.getStaffEmail());
        exitsStaff.setStaffPhone(staffDTO.getStaffPhone());
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
