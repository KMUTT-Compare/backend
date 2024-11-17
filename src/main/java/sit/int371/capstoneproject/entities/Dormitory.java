package sit.int371.capstoneproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "dormitory") // ชื่อ collection ใน MongoDB
public class Dormitory {
    @Id // ทำให้ MongoDB รู้ว่านี่คือ field สำหรับ ID
    private ObjectId id;   //เป็น Object ID ที่เอาไว้ระบุตัวข้อมูล

    private int dormId;
    private String name;
    private DormitoryStatusEnum status; // ใช้ enum ก็ได้ในกรณีที่ต้องการความปลอดภัย
    private Address address;
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
    private int staffId;

    // Nested Address Class
    @Getter
    @Setter
    public static class Address {
        private int dormNumber;
        private String street;
        private String subdistrict;
        private String district;
        private String province;
        private String postalCode;

    }
}
