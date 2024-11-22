package sit.int371.capstoneproject.repositories;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.File;

import java.util.List;
import java.util.Optional;


public interface FileRepository extends MongoRepository<File, ObjectId> {
    Optional<File> findByFileId(String id);
    List<File> findByStaffId(Integer id);
}
