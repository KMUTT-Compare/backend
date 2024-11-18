package sit.int371.capstoneproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateDormService;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.DormitoryStaffNameDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.services.DormitoryService;

import java.util.List;

@RestController
@RequestMapping("/api/dormitories")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    @Autowired
    private SequenceGenerateDormService sequenceGenerateDormService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;


    //Get All Dormitories
    @GetMapping
    public ResponseEntity<List<DormitoryStaffNameDTO>> getAllDormitories() {
        List<DormitoryStaffNameDTO> dormitories = dormitoryService.getAllDormitories(); // ดึง staffName จาก Staff collection
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
    public DormitoryDTO createdDormitory(@RequestBody DormitoryDTO dormitoryDTO){
        //generate dormitory id
        dormitoryDTO.setDormId((int) sequenceGenerateDormService.generateSequence(Dormitory.SEQUENCE_NAME));
        Dormitory dormitory = modelMapper.map(dormitoryDTO, Dormitory.class);
        return dormitoryService.createDorm(dormitory);
    }

    @PutMapping("/{id}")
    public DormitoryDTO updatedDorm(@PathVariable Integer id, @RequestBody DormitoryDTO dormitoryDTO){

        return dormitoryService.updateDorm(id, dormitoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedDorm(@PathVariable Integer id){
        String message = dormitoryService.deleteDorm(id);
        return ResponseEntity.ok(message);
    }
}
