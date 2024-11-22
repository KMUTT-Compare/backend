package sit.int371.capstoneproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadReturnDTO {
    private String fileId;
    private String fileName;
    private String uploadDate;
    private String fileUrl;
}
