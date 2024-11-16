package sit.int371.capstoneproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "staff") // ชื่อ collection ใน MongoDB
public class Staff {
    @Id // ทำให้ MongoDB รู้ว่านี่คือ field สำหรับ ID
    private ObjectId id;   //เป็น Object ID ที่เอาไว้ระบุตัวข้อมูล

    private int staffId;
    private String staffName;
    private String address;
    private String email;
    private String phone;
}
