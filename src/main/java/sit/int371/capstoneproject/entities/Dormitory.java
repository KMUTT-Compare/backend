package sit.int371.capstoneproject.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "dormitory") // ชื่อ collection ใน MongoDB
public class Dormitory {
    @Id // ทำให้ MongoDB รู้ว่านี่คือ field สำหรับ ID
    private ObjectId id;   //เป็น Object ID ที่เอาไว้ระบุตัวข้อมูล

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "dorm_sequence";
    @NotNull(message = "Dormitory Id cannot be null")
    private int dormId;
    @NotEmpty(message = "Dormitory name cannot be empty")
    @Size(max = 50, message = "Dormitory name must not exceed 50 characters")
    private String dormName;
    @NotNull(message = "Dormitory status cannot be null")
    private DormitoryStatusEnum status;
    @Valid //เช็คว่า fields ใน Address ครบหรือยัง
    private Address address;
    @Min(value = 1, message = "Room count must be at least 1")
    private int roomCount;
    @NotNull(message = "Dormitory type cannot be null")
    private DormitoryTypeEnum type;
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than 0")
    private Decimal128 size;
    @DecimalMin(value = "0.0", inclusive = false, message = "Min price must be greater than 0")
    private Decimal128 min_price;
    @DecimalMin(value = "0.0", inclusive = false, message = "Max price be greater than 0")
    private Decimal128 max_price;
    @DecimalMin(value = "0.0", inclusive = false, message = "Distance be greater than 0")
    private Decimal128 distance;
    @NotNull(message = "Creation date cannot be null")
    private Date created_at;
    @NotNull(message = "Updated date cannot be null")
    private Date updated_at;
    private List<String> image;
    @Size(min = 1, message = "At least one Building facility must be provided")
    private List<String> building_facility;
    @Size(min = 1, message = "At least one Room facility must be provided")
    private List<String> room_facility;
    @Min(value = 0, message = "Score cannot be less than 0")
    @Max(value = 5, message = "Score cannot be greater than 5")
    private int score;
    @NotNull(message = "Staff ID cannot be null")
    private int staffId;

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
