package sit.int371.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;
import org.springframework.data.mongodb.core.mapping.DBRef;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.DormitoryStatusEnum;
import sit.int371.capstoneproject.entities.DormitoryTypeEnum;
import sit.int371.capstoneproject.entities.Staff;


import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DormitoryDTO {
    private int dormId;
    private String name;
    private DormitoryStatusEnum status;
    private Dormitory.Address address; // ใช้ Address class จาก Dormitory หรือสร้างใหม่ตามความต้องการ
    private int roomCount;
    private DormitoryTypeEnum type;
    private Decimal128 size;
    private Decimal128 min_price;
    private Decimal128 max_price;
    private Decimal128 distance;
    private Date created_at;
    private Date updated_at;
    private List<String> image;
    private List<String> building_facility;
    private List<String> room_facility;
    private int staff; // ตัวอย่างการเก็บ staffId แทนการเก็บ staff object ทั้งหมด


}
