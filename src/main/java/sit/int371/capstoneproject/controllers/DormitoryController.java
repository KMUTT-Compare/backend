package sit.int371.capstoneproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
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
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get All Dormitories
//    @GetMapping("")
//    public List<DormitoryDTO> getAllDormDTO() {
//        List<Dormitory> dormitoryList = dormitoryService.getAllDormitory();
//        return listMapper.mapList(dormitoryList, DormitoryDTO.class, modelMapper);
//    }

    @GetMapping
    public ResponseEntity<List<DormitoryStaffNameDTO>> getAllDormitories() {
        List<DormitoryStaffNameDTO> dormitories = dormitoryService.getAllDormitories();
        return ResponseEntity.ok(dormitories);
    }

    //Get Dormitory by id
//    @GetMapping("/{id}")
//    public DormitoryDTO getDormDTO(@PathVariable Integer id){
//        return modelMapper.map(dormitoryService.getDormById(id), DormitoryDTO.class);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<DormitoryStaffNameDTO> getDormitoryById(@PathVariable Integer id) {
        DormitoryStaffNameDTO dormitory = dormitoryService.getDormById(id);
        return ResponseEntity.ok(dormitory);
    }

    //Create Dormitory
    @PostMapping("")
    public DormitoryDTO createdDormitory(@RequestBody DormitoryDTO dormitoryDTO){
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
