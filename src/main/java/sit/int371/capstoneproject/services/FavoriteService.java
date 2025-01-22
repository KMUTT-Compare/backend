package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.FavDormDTO;
import sit.int371.capstoneproject.dtos.FavoriteDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.Favorite;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.FavoriteRepository;
import sit.int371.capstoneproject.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    //Method -find all favorites
    public List<FavDormDTO> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();

        if (favorites.isEmpty()) {
            throw new ResourceNotFoundException("Favorites not found!");
        }

        // แปลง List<Favorite> เป็น List<FavDormDTO>
        return favorites.stream().map(this::convertToFavDormDTO).collect(Collectors.toList());
    }

    //Method -find favorite by id
    public FavDormDTO getFavById(Integer id){
        Favorite favorite = favoriteRepository.findByFavId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite id " + id + " not found !!!"));

        return convertToFavDormDTO(favorite);
    }

    //Method -create favorite
    public FavoriteDTO createFav(Favorite favorite){
        // ตรวจสอบว่า dormId และ userId เป็น 0 หรือไม่
        List<String> errors = new ArrayList<>();
        if (!dormitoryRepository.existsByDormId(favorite.getDormId())) {
            errors.add("Dorm id " + favorite.getDormId() + " not found");
        }
        if (!userRepository.existsByUserId(favorite.getUserId())) {
            errors.add("User id " + favorite.getUserId() + " not found");
        }

        // หากมีข้อผิดพลาดใดๆ ให้โยน exception พร้อมข้อความรวม
        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(String.join(", ", errors));
        }

        Favorite addFav = new Favorite();
        addFav.setFavId(favorite.getFavId());
        addFav.setDormId(favorite.getDormId());
        addFav.setUserId(favorite.getUserId());
        return modelMapper.map(favoriteRepository.save(addFav), FavoriteDTO.class);
    }

    //Method -update favorite
    public FavoriteDTO updateFav(Integer id, FavoriteDTO favoriteDTO){
        Favorite exitsFav = favoriteRepository.findByFavId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + " does not exited!!! "));

        // ตรวจสอบว่า dormId และ userId เป็น 0 หรือไม่
        List<String> errors = new ArrayList<>();
        if (!dormitoryRepository.existsByDormId(favoriteDTO.getDormId())) {
            errors.add("Dorm id " + favoriteDTO.getDormId() + " not found");
        }
        if (!userRepository.existsByUserId(favoriteDTO.getUserId())) {
            errors.add("User id " + favoriteDTO.getUserId() + " not found");
        }

        // หากมีข้อผิดพลาดใดๆ ให้โยน exception พร้อมข้อความรวม
        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(String.join(", ", errors));
        }
        exitsFav.setDormId(favoriteDTO.getDormId());
        exitsFav.setUserId(favoriteDTO.getUserId());
        return modelMapper.map(favoriteRepository.save(exitsFav), FavoriteDTO.class);
    }

    //Method -delete favorite
    public String deleteFav(Integer id){
        if (favoriteRepository.existsByFavId(id)){
            favoriteRepository.deleteByFavId(id);
            return "Favorite with ID " + id + " has been deleted successfully!";
        }else {
            throw new ResourceNotFoundException("Favorite with ID " + id + " dose not exited!!!");
        }
    }

    // Convert Method - Convert Favorite to FavDormDTO
    private FavDormDTO convertToFavDormDTO(Favorite favorite) {
        // ดึงข้อมูล Dormitory จาก repository
        Dormitory dormitory = dormitoryRepository.findByDormId(favorite.getDormId())
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + favorite.getDormId() + " not found !!!"));

        // แปลง Favorite เป็น FavDormDTO
        FavDormDTO favDormDTO = modelMapper.map(favorite, FavDormDTO.class);

        // ตั้งค่าเพิ่มเติมจาก Dormitory
        favDormDTO.setDormName(dormitory.getDormName());
        favDormDTO.setSize(dormitory.getSize());
        favDormDTO.setMin_price(dormitory.getMin_price());
        favDormDTO.setMax_price(dormitory.getMax_price());
        favDormDTO.setDistance(dormitory.getDistance());
        favDormDTO.setScore(dormitory.getScore());

        return favDormDTO;
    }
}
