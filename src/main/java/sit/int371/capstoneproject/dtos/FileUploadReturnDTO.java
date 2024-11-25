package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadReturnDTO {
    @NotNull(message = "File ID cannot be null")
    private String fileId;
    @NotEmpty(message = "File name cannot be empty")
//    @Size(max = 100, message = "File name must not exceed 100 characters")
    private String fileName;
    @NotNull(message = "Updated date cannot be null")
    private String uploadDate;
    private String fileUrl;
}
