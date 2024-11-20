package sit.int371.capstoneproject.entities;

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
    private int favId;
    private int dormId;
    private int userId;
}
