package sit.int371.capstoneproject.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.DormitoryStatusEnum;
import sit.int371.capstoneproject.entities.DormitoryTypeEnum;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DormitoryStaffNameDTO {
    @NotNull(message = "Dormitory Id cannot be null")
    private int dormId;
    @NotEmpty(message = "Dormitory name cannot be empty")
    @Size(max = 50, message = "Dormitory name must not exceed 50 characters")
    private String name;
    @NotNull(message = "Dormitory status cannot be null")
    private DormitoryStatusEnum status;
    @Valid //เช็คว่า fields ใน Address ครบหรือยัง
    private Dormitory.Address address;
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
    @NotNull(message = "Staff ID cannot be null")
    private int staffId;
    private String staffName;

}