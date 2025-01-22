package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;

@Getter
@Setter
public class FavDormDTO {
    @NotNull(message = "Favorite Id cannot be null")
    private int favId;
    @NotNull(message = "User Id is required")
    @Min(value = 1, message = "User Id must be greater than 0")
    private int userId;

    @NotNull(message = "Dormitory Id is required")
    @Min(value = 1, message = "Dormitory Id must be greater than 0")
    private int dormId;
    @NotEmpty(message = "Dormitory name cannot be empty")
    @Size(max = 50, message = "Dormitory name must not exceed 50 characters")
    private String dormName;
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than 0")
    private Decimal128 size;
    @DecimalMin(value = "0.0", inclusive = false, message = "Min price must be greater than 0")
    private Decimal128 min_price;
    @DecimalMin(value = "0.0", inclusive = false, message = "Max price be greater than 0")
    private Decimal128 max_price;
    @DecimalMin(value = "0.0", inclusive = false, message = "Distance be greater than 0")
    private Decimal128 distance;
    @Min(value = 1, message = "Score cannot be less than 1")
    @Max(value = 5, message = "Score cannot be greater than 5")
    private int score;
}
