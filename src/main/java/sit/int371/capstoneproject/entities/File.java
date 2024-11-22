package sit.int371.capstoneproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "image")
public class File {
    private String fileId;
    private String fileName;
    private String uploadDate;
    private String fileUrl;
    private int staffId;

}
