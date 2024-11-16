package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.repositories.DormitoryRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DormitoryService {
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    //Method -find All dormitories
    public List<Dormitory> getAllDormitory(){
        return dormitoryRepository.findAll();
    }

    //Method -find dormitory by id
    public Dormitory getDormById(Integer id){
        return dormitoryRepository.findByDormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + id + "not found !!!"));
    }
}
