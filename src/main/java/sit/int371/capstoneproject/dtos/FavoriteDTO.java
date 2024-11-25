package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    @NotNull(message = "Favorite ID cannot be null")
    private int favId;
    @NotNull(message = "Dorm ID is required")
    @Min(value = 1, message = "Dorm ID must be greater than 0")
    private int dormId;
    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;
}
