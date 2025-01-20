package sit.int371.capstoneproject.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.Form;

import java.util.Optional;

public interface FormRepository extends MongoRepository<Form, ObjectId> {
    Optional<Form> findByFormId(Integer id);
    boolean existsByFormId(Integer id);
    //generate form id
    Optional<Form> findTopByOrderByFormIdDesc();
}
