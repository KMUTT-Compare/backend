package sit.int371.capstoneproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;

    private int userId;
    private String username;
    private String password;
    private String email;
    private UserRoleEnum role;
    private Date createdOn;
    private Date updatedOn;
}
