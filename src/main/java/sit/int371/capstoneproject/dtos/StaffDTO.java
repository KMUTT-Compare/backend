package sit.int371.capstoneproject.dtos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sit.int371.capstoneproject.entities.Staff;

@Getter
@Setter
public class StaffDTO {
    @NotNull(message = "Staff Id cannot be null")
    private int staffId;
    @NotEmpty(message = "Staff name cannot be empty")
    @Size(max = 50, message = "Staff name must not exceed 50 characters")
    private String staffName;
    @Valid //เช็คว่า fields ใน Address ครบหรือยัง
    private Staff.Address staffAddress;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String staffEmail;
    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Min(value = 0, message = "Phone number cannot be negative")
    private String staffPhone;
}
