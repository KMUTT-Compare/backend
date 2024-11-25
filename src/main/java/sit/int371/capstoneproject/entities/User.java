package sit.int371.capstoneproject.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "user_sequence";
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
