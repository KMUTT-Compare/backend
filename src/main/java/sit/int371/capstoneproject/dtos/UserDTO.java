package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sit.int371.capstoneproject.entities.UserRoleEnum;

import java.util.Date;

@Getter
@Setter
public class UserDTO {

    @NotNull(message = "User Id cannot be null")
    private int userId;
    @NotEmpty(message = "User name cannot be empty")
    @Size(max = 50, message = "User name must not exceed 50 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "User role cannot be null")
    private UserRoleEnum role;
    @NotNull(message = "Creation date cannot be null")
    private Date createdOn;
    @NotNull(message = "Updated date cannot be null")
    private Date updatedOn;
}
