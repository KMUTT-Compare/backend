package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDTO {
    @NotNull(message = "Staff ID cannot be null")
    private int staffId;
    @NotEmpty(message = "Staff name cannot be empty")
    @Size(max = 50, message = "Staff name must not exceed 50 characters")
    private String staffName;
    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 200, message = "Staff name must not exceed 50 characters")
    private String address;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Min(value = 0, message = "Phone number cannot be negative")
    private String phone;
}
