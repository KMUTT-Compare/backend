package sit.int371.capstoneproject.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import sit.int371.capstoneproject.dtos.StaffDTO;

@Getter
@Setter
@Document(collection = "staff") // ชื่อ collection ใน MongoDB
public class Staff {
    @Id // ทำให้ MongoDB รู้ว่านี่คือ field สำหรับ ID
    private ObjectId id;   //เป็น Object ID ที่เอาไว้ระบุตัวข้อมูล

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "staff_sequence";
    @NotNull(message = "Staff Id cannot be null")
    private int staffId;
    @NotEmpty(message = "Staff name cannot be empty")
    @Size(max = 50, message = "Staff name must not exceed 50 characters")
    private String staffName;
    @Valid //เช็คว่า fields ใน Address ครบหรือยัง
    private Address address;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Min(value = 0, message = "Phone number cannot be negative")
    private String phone;


    // Nested Address Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @NotEmpty(message = "Dormitory number cannot be empty")
        private String dormNumber;
        @NotEmpty(message = "Street cannot be empty")
        private String street;
        @NotEmpty(message = "Subdistrict cannot be empty")
        private String subdistrict;
        @NotEmpty(message = "District cannot be empty")
        private String district;
        @NotEmpty(message = "Province cannot be empty")
        private String province;
        @NotEmpty(message = "Postal code cannot be empty")
        @Pattern(regexp = "\\d{5}", message = "Postal code must be a 5-digit number")
        private String postalCode;
    }
}