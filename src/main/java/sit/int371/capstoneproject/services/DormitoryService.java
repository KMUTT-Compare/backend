package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.DormitoryStaffNameDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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
    public DormitoryStaffNameDTO getDormById(Integer id) {
        Dormitory dormitory = dormitoryRepository.findByDormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + id + " not found !!!"));

        DormitoryStaffNameDTO dto = new DormitoryStaffNameDTO();
        BeanUtils.copyProperties(dormitory, dto); // คัดลอกข้อมูลทั่วไปจาก Dormitory

        // ดึง staffName จาก Staff collection
        staffRepository.findByStaffId(dormitory.getStaffId())
                .ifPresent(staff -> dto.setStaffName(staff.getStaffName()));

        return dto;
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
        return modelMapper.map(dormitoryRepository.save(addDorm), DormitoryDTO.class);
    }

    //Method -update dormitory
    public DormitoryDTO updateDorm(Integer id, DormitoryDTO dormitoryDTO){
        Dormitory exitsDorm = dormitoryRepository.findByDormId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + "does not exited!!!"));
        exitsDorm.setName(dormitoryDTO.getName());
        exitsDorm.setStatus(dormitoryDTO.getStatus());
        exitsDorm.setAddress(dormitoryDTO.getAddress());
        exitsDorm.setRoomCount(dormitoryDTO.getRoomCount());
        exitsDorm.setType(dormitoryDTO.getType());
        exitsDorm.setSize(dormitoryDTO.getSize());
        exitsDorm.setMin_price(dormitoryDTO.getMin_price());
        exitsDorm.setMax_price(dormitoryDTO.getMax_price());
        exitsDorm.setDistance(dormitoryDTO.getDistance());
        exitsDorm.setCreated_at(dormitoryDTO.getCreated_at());
        exitsDorm.setUpdated_at(dormitoryDTO.getUpdated_at());
        exitsDorm.setImage(dormitoryDTO.getImage());
        exitsDorm.setBuilding_facility(dormitoryDTO.getBuilding_facility());
        exitsDorm.setRoom_facility(dormitoryDTO.getRoom_facility());
        exitsDorm.setStaffId(dormitoryDTO.getStaffId());
        Dormitory updatedDormitory = dormitoryRepository.save(exitsDorm);
        return modelMapper.map(updatedDormitory, DormitoryDTO.class);
    }

    //Method -delete dormitory
    public String deleteDorm(Integer id){
        if (dormitoryRepository.existsByDormId(id)){
            dormitoryRepository.deleteByDormId(id);
            return "Dormitory with ID " + id + " has been deleted successfully!";
        }else {
            throw new ResourceNotFoundException("Dormitory with ID " + id + " dose not exited!!!");
        }
    }


    //เรียกเข้า staffName มาโชว์ด้วย
    public List<DormitoryStaffNameDTO> getAllDormitories() {
        List<Dormitory> dormitories = dormitoryRepository.findAll();
        List<DormitoryStaffNameDTO> dtoList = new ArrayList<>();

        for (Dormitory dormitory : dormitories) {
            DormitoryStaffNameDTO dto = new DormitoryStaffNameDTO();
            BeanUtils.copyProperties(dormitory, dto); // คัดลอกข้อมูลทั่วไปจาก Dormitory

            // ดึง staffName จาก Staff collection
            staffRepository.findByStaffId(dormitory.getStaffId())
                    .ifPresent(staff -> dto.setStaffName(staff.getStaffName()));

            dtoList.add(dto);
        }

        return dtoList;
    }
}