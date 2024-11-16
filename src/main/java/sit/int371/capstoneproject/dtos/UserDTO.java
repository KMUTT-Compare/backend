package sit.int371.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int371.capstoneproject.entities.UserRoleEnum;

import java.util.Date;

@Getter
@Setter
public class UserDTO {

    private int userId;
    private String username;
    private String password;
    private String email;
    private UserRoleEnum role;
    private Date createdOn;
    private Date updatedOn;
}
