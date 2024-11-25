package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.DormitoryStaffNameDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DormitoryService {
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StaffRepository staffRepository;


    //Method -find dormitory by id
    public DormitoryStaffNameDTO getDormById(Integer id) {
        Dormitory dormitory = dormitoryRepository.findByDormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + id + " not found !!!"));

        DormitoryStaffNameDTO staffNameDTO = new DormitoryStaffNameDTO();
        BeanUtils.copyProperties(dormitory, staffNameDTO); // คัดลอกข้อมูลทั่วไปจาก Dormitory

        // ดึง staffName จาก Staff collection
        staffRepository.findByStaffId(dormitory.getStaffId())
                .ifPresent(staff -> staffNameDTO.setStaffName(staff.getStaffName()));

        return staffNameDTO;
    }

    //Method -create dormitory
    public DormitoryDTO createDorm(Dormitory dormitory) {
        // ตรวจสอบว่า staffId มีอยู่ในฐานข้อมูลหรือไม่
        if (!staffRepository.existsByStaffId(dormitory.getStaffId())) {
            throw new ResourceNotFoundException("Staff id " + dormitory.getStaffId() + " not exited!!!");
        }

        Dormitory addDorm = new Dormitory();
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
        return modelMapper.map(dormitoryRepository.save(dormitory), DormitoryDTO.class);
    }

    //Method -update dormitory
    public DormitoryDTO updateDorm(Integer id, DormitoryDTO dormitoryDTO){
        Dormitory exitsDorm = dormitoryRepository.findByDormId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + " does not exited!!! "));

        // ตรวจสอบว่า staffId มีอยู่ในฐานข้อมูลหรือไม่
        if (!staffRepository.existsByStaffId(dormitoryDTO.getStaffId())) {
            throw new ResourceNotFoundException("Staff id " + dormitoryDTO.getStaffId() + " not exited!!!");
        }
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
            DormitoryStaffNameDTO staffNameDTO = new DormitoryStaffNameDTO();
            BeanUtils.copyProperties(dormitory, staffNameDTO); // คัดลอกข้อมูลทั่วไปจาก Dormitory

            // ดึง staffName จาก Staff collection
            staffRepository.findByStaffId(dormitory.getStaffId())
                    .ifPresent(staff -> staffNameDTO.setStaffName(staff.getStaffName()));

            dtoList.add(staffNameDTO);
        }

        return dtoList;
    }


    // ทำ Sorting Dormitories ตามราคาของหอพักทั้ง ราคาสูงสุด, ราคาต่ำสุด
    // Sort Dormitory by min_price
    public List<DormitoryDTO> sortDormByMinPrice() {
        List<Dormitory> dorms = dormitoryRepository.findAll(Sort.by(Sort.Direction.ASC, "min_price"));
        return dorms.stream()
                .map(dorm -> modelMapper.map(dorm, DormitoryDTO.class))
                .collect(Collectors.toList());
    }

    // Sort Dormitory by max_price
    public List<DormitoryDTO> sortDormByMaxPrice() {
        List<Dormitory> dorms = dormitoryRepository.findAll(Sort.by(Sort.Direction.DESC, "max_price"));
        return dorms.stream()
                .map(dorm -> modelMapper.map(dorm, DormitoryDTO.class))
                .collect(Collectors.toList());
    }
}
