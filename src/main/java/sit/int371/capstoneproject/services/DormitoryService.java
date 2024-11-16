package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DormitoryService {
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StaffRepository staffRepository;

    //Method -find All dormitories
    public List<Dormitory> getAllDormitory(){
        return dormitoryRepository.findAll();
    }

    //Method -find dormitory by id
    public Dormitory getDormById(Integer id){
        return dormitoryRepository.findByDormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + id + "not found !!!"));
    }

    //Method -create dormitory
    public DormitoryDTO createDorm(Dormitory dormitory) {
        // ตรวจสอบว่ามี Staff ID นี้อยู่หรือไม่
        Optional<Staff> staff = staffRepository.findByStaffId(dormitory.getStaffId());
        if (staff.isEmpty()) {
            throw new RuntimeException("Staff not found with ID: " + dormitory.getStaffId());
        }

        Dormitory addDorm = new Dormitory();
        // Set ค่าให้กับ addDorm จาก dormitory ที่รับเข้ามา
        addDorm.setDormId(dormitory.getDormId());
        addDorm.setName(dormitory.getName());
        addDorm.setStatus(dormitory.getStatus());
        addDorm.setAddress(dormitory.getAddress());
        addDorm.setRoomCount(dormitory.getRoomCount());
        addDorm.setType(dormitory.getType());
        addDorm.setSize(dormitory.getSize());
        addDorm.setMin_price(dormitory.getMin_price());
        addDorm.setMax_price(dormitory.getMax_price());
        addDorm.setDistance(dormitory.getDistance());
        addDorm.setCreated_at(dormitory.getCreated_at());
        addDorm.setUpdated_at(dormitory.getUpdated_at());
        addDorm.setImage(dormitory.getImage());
        addDorm.setBuilding_facility(dormitory.getBuilding_facility());
        addDorm.setRoom_facility(dormitory.getRoom_facility());
        addDorm.setStaffId(dormitory.getStaffId());


        // บันทึก Dormitory และแปลงเป็น DTO
        return modelMapper.map(dormitoryRepository.save(addDorm), DormitoryDTO.class);
    }


}
