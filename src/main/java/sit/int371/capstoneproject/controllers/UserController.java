package sit.int371.capstoneproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateUserService;
import sit.int371.capstoneproject.dtos.UserDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.User;
import sit.int371.capstoneproject.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SequenceGenerateUserService sequenceGenerateUserService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get Staff All Staff
    @GetMapping("")
    public List<UserDTO> getAllUserDTO(){
        List<User> userList = userService.getAllUser();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    }

    @GetMapping("/{id}")
    public UserDTO getUserDTO(@PathVariable Integer id){
        return modelMapper.map(userService.getUserById(id), UserDTO.class);
    }

    @PostMapping("")
    public UserDTO createdUser(@RequestBody UserDTO userDTO){
        //generate dormitory id
        userDTO.setUserId((int) sequenceGenerateUserService.generateSequence(User.SEQUENCE_NAME));
        User user = modelMapper.map(userDTO, User.class);
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserDTO updatedUser(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        //generate dormitory id
        userDTO.setUserId((int) sequenceGenerateUserService.generateSequence(User.SEQUENCE_NAME));
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedUser(@PathVariable Integer id){
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }
}
