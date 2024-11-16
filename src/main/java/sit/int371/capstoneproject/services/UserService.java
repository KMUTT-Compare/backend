package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.UserDTO;
import sit.int371.capstoneproject.entities.User;
import sit.int371.capstoneproject.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Method -find All users
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //Methode -find user by id
    public User getUserById(Integer id){
        return userRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found !!!"));
    }

    //Method -create user
    public UserDTO createUser(User user){
        User addUser = new User();
        addUser.setUserId(user.getUserId());
        addUser.setUsername(user.getUsername());
        addUser.setPassword(user.getPassword());
        addUser.setRole(user.getRole());
        addUser.setCreatedOn(user.getCreatedOn());
        addUser.setUpdatedOn(user.getUpdatedOn());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    //Method -update user
    public UserDTO updateUser(Integer id, UserDTO userDTO){
        User exitsUser = userRepository.findByUserId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + " does not exited!!!"));
        exitsUser.setUsername(userDTO.getUsername());
        exitsUser.setPassword(userDTO.getPassword());
        exitsUser.setRole(userDTO.getRole());
        exitsUser.setCreatedOn(userDTO.getCreatedOn());
        exitsUser.setUpdatedOn(userDTO.getUpdatedOn());
        User updatedUser = userRepository.save(exitsUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

}
