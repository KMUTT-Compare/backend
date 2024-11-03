package sit.int371.capstoneproject.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.Staff;

import java.util.Optional;

public interface StaffRepository extends MongoRepository<Staff, ObjectId> {
    Optional<Staff> findByStaffId(Integer id); // เพิ่มเมธอดสำหรับค้นหาตาม Staff id *เพราะ mongoDB ค้นหา id จะเป็น ObjectId
}
