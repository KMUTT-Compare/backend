package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.DormitoryStaffDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class DormitoryService {
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StaffRepository staffRepository;

    //Method -find all dormitory และเรียกเข้า staffName, staffPhone, staffEmail มาโชว์ด้วย
    public List<DormitoryStaffDTO> getAllDormitories() {
        List<Dormitory> dormitories = dormitoryRepository.findAll();
        List<DormitoryStaffDTO> dtoList = new ArrayList<>();

        for (Dormitory dormitory : dormitories) {
            DormitoryStaffDTO dormStaffDTO = new DormitoryStaffDTO();
            BeanUtils.copyProperties(dormitory, dormStaffDTO); // คัดลอกข้อมูลทั่วไปจาก Dormitory

            // ดึง staffName, staffPhone จาก Staff collection
            staffRepository.findByStaffId(dormitory.getStaffId())
                    .ifPresent(staff -> {
                        dormStaffDTO.setStaffName(staff.getStaffName());
                        dormStaffDTO.setStaffEmail(staff.getStaffEmail());
                        dormStaffDTO.setStaffPhone(staff.getStaffPhone());
                    });

            dtoList.add(dormStaffDTO);
        }

        return dtoList;
    }

    //Method -find dormitory by id
    public DormitoryStaffDTO getDormById(Integer id) {
        Dormitory dormitory = dormitoryRepository.findByDormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + id + " not found !!!"));

        DormitoryStaffDTO dormStaffDTO = new DormitoryStaffDTO();
        BeanUtils.copyProperties(dormitory, dormStaffDTO); // คัดลอกข้อมูลทั่วไปจาก Dormitory

        // ดึง staffName, staffPhone จาก Staff collection
        staffRepository.findByStaffId(dormitory.getStaffId())
                .ifPresent(staff -> {
                    dormStaffDTO.setStaffName(staff.getStaffName());
                    dormStaffDTO.setStaffEmail(staff.getStaffEmail());
                    dormStaffDTO.setStaffPhone(staff.getStaffPhone());
                });

        return dormStaffDTO;
    }

    //Method -create dormitory + count all facilities
    public DormitoryDTO createDorm(Dormitory dormitory) {
        // ตรวจสอบว่า staffId มีอยู่ในฐานข้อมูลหรือไม่
        if (!staffRepository.existsByStaffId(dormitory.getStaffId())) {
            throw new ResourceNotFoundException("Dormitory id " + dormitory.getStaffId() + " not exited!!!");
        }
        // คำนวณจำนวน facilities
        int totalFacilities = 0;
        if (dormitory.getRoom_facility() != null) {
            totalFacilities += dormitory.getRoom_facility().size();
        }
        if (dormitory.getBuilding_facility() != null) {
            totalFacilities += dormitory.getBuilding_facility().size();
        }
        // กำหนดค่า countFacilities
        dormitory.setCount_facilities(totalFacilities);

        // บันทึกลง DB
        Dormitory addDorm = new Dormitory();
        addDorm.setDormId(dormitory.getDormId());
        addDorm.setDormName(dormitory.getDormName());
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
        addDorm.setScore(dormitory.getScore());
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

        // บันทึกลง DB
        exitsDorm.setDormName(dormitoryDTO.getDormName());
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
        // คำนวณและอัปเดตค่า countFacility
        int totalFacilities = calculateFacilitiesCount(dormitoryDTO.getBuilding_facility(), dormitoryDTO.getRoom_facility());
        exitsDorm.setCount_facilities(totalFacilities);
        exitsDorm.setScore(dormitoryDTO.getScore());
        exitsDorm.setStaffId(dormitoryDTO.getStaffId());
        Dormitory updatedDormitory = dormitoryRepository.save(exitsDorm);
        return modelMapper.map(updatedDormitory, DormitoryDTO.class);
    }

    // Method การคำนวณจำนวน facilities
    private int calculateFacilitiesCount(List<String> buildingFacilities, List<String> roomFacilities) {
        // นับจำนวน facilities ใน building_facility และ room_facility รวมกัน
        int countBuildingFacilities = buildingFacilities != null ? buildingFacilities.size() : 0;
        int countRoomFacilities = roomFacilities != null ? roomFacilities.size() : 0;
        // รวมจำนวนทั้งหมด
        return countBuildingFacilities + countRoomFacilities;
    }

    //Method -delete dormitory
    public String deleteDorm(Integer id){
        if (dormitoryRepository.existsByDormId(id)){
            dormitoryRepository.deleteByDormId(id);
            return "Dormitory with Id " + id + " has been deleted successfully!";
        }else {
            throw new ResourceNotFoundException("Dormitory with Id " + id + " dose not exited!!!");
        }
    }

}
