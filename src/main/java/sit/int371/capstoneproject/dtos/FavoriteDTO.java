package sit.int371.capstoneproject.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    @NotNull(message = "Favorite ID cannot be null")
    private int favId;
    @Valid
    @NotNull(message = "Dorm ID is required")
    private int dormId;
    @Valid
    @NotNull(message = "User ID is required")
    private int userId;
}
