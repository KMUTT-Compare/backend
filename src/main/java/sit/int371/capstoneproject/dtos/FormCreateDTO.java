package sit.int371.capstoneproject.dtos;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class FormCreateDTO {
    @NotNull(message = "Form Id cannot be null")
    private int formId;
    @NotNull(message = "Form date cannot be null")
    private Date form_date;
    @NotNull(message = "User ID cannot be null")
    private int userId;
    @NotEmpty(message = "User name cannot be empty")
    @Size(max = 50, message = "User name must not exceed 50 characters")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Min(value = 0, message = "Phone number cannot be negative")
    private String phone;
    @NotNull(message = "Form in date cannot be null")
    private Date date_in;
    @NotNull(message = "Form out date cannot be null")
    private Date date_out;
    @Size(max = 200, message = "Description details must not exceed 200 characters")
    private String description = "null";

    @NotNull(message = "Dormitory ID cannot be null")
    private int dormId;

}
