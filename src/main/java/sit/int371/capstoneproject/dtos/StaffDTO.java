package sit.int371.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDTO {
    private Integer staffId;
    private String staffName;
    private String address;
    private String email;
    private String phone;
}
