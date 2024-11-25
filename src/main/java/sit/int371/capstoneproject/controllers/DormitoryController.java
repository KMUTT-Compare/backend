package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.autoId.SequenceGenerateDormService;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.DormitoryStaffNameDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.services.DormitoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://127.0.0.1:5173/","https://cp24kk2.sit.kmutt.ac.th/"})
@RequestMapping("/api/dormitories")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    private SequenceGenerateDormService sequenceGenerateDormService;

    @Autowired
    private ModelMapper modelMapper;


    //Get All Dormitories
    @GetMapping
    public ResponseEntity<List<DormitoryStaffNameDTO>> getAllDormitories() {
        List<DormitoryStaffNameDTO> dormitories = dormitoryService.getAllDormitories(); // ดึง staffName จาก Staff collection
        // เช็คว่ามีข้อมูลหรือไม่ ถ้าไม่มีข้อมูลให้ส่ง 404
        if (dormitories.isEmpty()) {
            throw new ResourceNotFoundException("No staff found!");
        }
        return ResponseEntity.ok(dormitories);
    }

    //Get Dormitory by id
    @GetMapping("/{id}")
    public ResponseEntity<DormitoryStaffNameDTO> getDormitoryById(@PathVariable Integer id) {
        DormitoryStaffNameDTO dormitory = dormitoryService.getDormById(id); // ดึง staffName จาก Staff collection
        return ResponseEntity.ok(dormitory);
    }

    //Create Dormitory
    @PostMapping("")
    public DormitoryDTO createdDormitory(@Valid @RequestBody DormitoryDTO dormitoryDTO){
        //generate dormitory id
        dormitoryDTO.setDormId((int) sequenceGenerateDormService.generateSequence(Dormitory.SEQUENCE_NAME));
        Dormitory dormitory = modelMapper.map(dormitoryDTO, Dormitory.class);
        return dormitoryService.createDorm(dormitory);
    }

    //Update Dormitory
    @PutMapping("/{id}")
    public DormitoryDTO updatedDorm(@PathVariable Integer id, @Valid @RequestBody DormitoryDTO dormitoryDTO){
        //generate dormitory id
        dormitoryDTO.setDormId((int) sequenceGenerateDormService.generateSequence(Dormitory.SEQUENCE_NAME));
        return dormitoryService.updateDorm(id, dormitoryDTO);
    }

    //Delete Dormitory
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedDorm(@PathVariable Integer id){
        String message = dormitoryService.deleteDorm(id);
        return ResponseEntity.ok(message);
    }

    //Get all dormitories by Min Price
    @GetMapping("/sort/min_price")
    public ResponseEntity<List<DormitoryDTO>> sortDormByMinPrice() {
        List<DormitoryDTO> dorms = dormitoryService.sortDormByMinPrice();
        return ResponseEntity.ok(dorms);
    }

    //Get all dormitories by Max Price
    @GetMapping("/sort/max_price")
    public ResponseEntity<List<DormitoryDTO>> sortDormByMaxPrice() {
        List<DormitoryDTO> dorms = dormitoryService.sortDormByMaxPrice();
        return ResponseEntity.ok(dorms);
    }
}
