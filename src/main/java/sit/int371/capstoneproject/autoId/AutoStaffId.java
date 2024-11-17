package sit.int371.capstoneproject.autoId;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "staff")
public class AutoStaffId {
    @Id
    private String id;
    private long seq;
}
