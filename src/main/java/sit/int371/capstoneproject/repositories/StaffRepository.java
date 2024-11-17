package sit.int371.capstoneproject.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import sit.int371.capstoneproject.entities.Staff;

import java.util.Optional;

public interface StaffRepository extends MongoRepository<Staff, ObjectId> {
    Optional<Staff> findByStaffId(Integer id); // เพิ่มเมธอดสำหรับค้นหาตาม Staff id *เพราะ mongoDB ค้นหา id จะเป็น ObjectId
    boolean existsByStaffId(Integer id); // boolean เพื่อเช็คว่า id นี้มีหรือไม่
    void deleteByStaffId(Integer id); // delete โดยไม่ต้องคืนค่า

    //generate staff id
    @Query(value = "{}", sort = "{ staffId: -1 }") // เรียงลำดับ staffId จากมากไปน้อย
    Optional<Staff> findTopByOrderByStaffIdDesc();
}
