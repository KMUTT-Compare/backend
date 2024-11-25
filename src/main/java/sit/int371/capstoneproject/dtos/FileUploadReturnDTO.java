package sit.int371.capstoneproject.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadReturnDTO {
    @NotNull(message = "File Id cannot be null")
    private String fileId;
    @NotEmpty(message = "File name cannot be empty")
    private String fileName;
    @NotNull(message = "Updated date cannot be null")
    private String uploadDate;
    private String fileUrl;
}
