package sit.int371.capstoneproject.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sit.int371.capstoneproject.entities.Favorite;

import java.util.Optional;

public interface FavoriteRepository extends MongoRepository<Favorite, ObjectId> {
    Optional<Favorite> findByFavId(Integer id);
    boolean existsByFavId(Integer id);
    void deleteByFavId(Integer id);
}
