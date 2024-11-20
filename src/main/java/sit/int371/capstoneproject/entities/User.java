package sit.int371.capstoneproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;

    @Transient // ไม่เก็บ field นี้ใน MongoDB
    public static final String SEQUENCE_NAME = "user_sequence";
    private int userId;
    private String username;
    private String password;
    private String email;
    private UserRoleEnum role;
    private Date createdOn;
    private Date updatedOn;
}
