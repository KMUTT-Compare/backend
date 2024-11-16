package sit.int371.capstoneproject.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.Dormitory;

import java.util.Optional;


public interface DormitoryRepository extends MongoRepository<Dormitory, ObjectId> {
    Optional<Dormitory> findByDormId(Integer id);
}
