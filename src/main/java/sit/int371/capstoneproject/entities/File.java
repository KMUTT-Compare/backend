package sit.int371.capstoneproject.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "image")
public class File {
    @NotNull(message = "File ID cannot be null")
    private String fileId;
    @NotEmpty(message = "File name cannot be empty")
//    @Size(max = 100, message = "Staff name must not exceed 100 characters")
    private String fileName;
    @NotNull(message = "Updated date cannot be null")
    private String uploadDate;
    private String fileUrl;
    @NotNull(message = "File ID cannot be null")
    private int staffId;

}
