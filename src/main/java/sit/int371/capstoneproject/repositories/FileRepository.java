package sit.int371.capstoneproject.repositories;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.File;

import java.util.List;



public interface FileRepository extends MongoRepository<File, ObjectId> {
    List<File> findByDormId(Integer id);
    boolean existsByFileId(String id); // boolean เพื่อเช็คว่า id นี้มีหรือไม่
    void deleteByFileId(String id); // delete โดยไม่ต้องคืนค่า
}
