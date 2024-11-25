package sit.int371.capstoneproject.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "favorite")
public class Favorite {
    @Id
    private ObjectId id;

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "favorite_sequence";
    @NotNull(message = "Favorite Id cannot be null")
    private int favId;
    @NotNull(message = "Dormitory Id is required")
    @Min(value = 1, message = "Dormitory Id must be greater than 0")
    private int dormId;
    @NotNull(message = "User Id is required")
    @Min(value = 1, message = "User Id must be greater than 0")
    private int userId;
}
