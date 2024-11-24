package sit.int371.capstoneproject.entities;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "staff") // ชื่อ collection ใน MongoDB
public class Staff {
    @Id // ทำให้ MongoDB รู้ว่านี่คือ field สำหรับ ID
    private ObjectId id;   //เป็น Object ID ที่เอาไว้ระบุตัวข้อมูล

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "staff_sequence";
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
