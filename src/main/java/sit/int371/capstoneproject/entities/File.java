package sit.int371.capstoneproject.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "image")
public class File {
    @NotNull(message = "File Id cannot be null")
    private String fileId;
    @NotEmpty(message = "File name cannot be empty")
    private String fileName;
    @NotNull(message = "Updated date cannot be null")
    private String uploadDate;
    private String fileUrl;
    @NotNull(message = "Staff Id cannot be null")
    private int staffId;
    private int dormId;
}
