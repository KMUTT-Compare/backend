package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.FavoriteDTO;
import sit.int371.capstoneproject.entities.Favorite;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.FavoriteRepository;
import sit.int371.capstoneproject.repositories.UserRepository;


import java.util.List;


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


    //Method -find All fav
    public List<Favorite> getAllFavorite(){
        List<Favorite> favorites = favoriteRepository.findAll();
        if(favorites.isEmpty()){
            throw new ResourceNotFoundException("Favorite not found!");
        }
        return favorites;
    }

    //Method -find favorite by id
    public Favorite getFavById(Integer id){
        return favoriteRepository.findByFavId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite id " + id + " not found !!!"));
    }

    //Method -create favorite
    public FavoriteDTO createFav(Favorite favorite){
        // ตรวจสอบว่า dormId มีอยู่ในฐานข้อมูลหรือไม่
        if (!dormitoryRepository.existsByDormId(favorite.getDormId())) {
            throw new ResourceNotFoundException("Dorm id " + favorite.getDormId() + " not exited!!!");
        }
        // ตรวจสอบว่า userId มีอยู่ในฐานข้อมูลหรือไม่
        if (!userRepository.existsByUserId(favorite.getUserId())) {
            throw new ResourceNotFoundException("User id " + favorite.getUserId() + " not exited!!!");
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
        // ตรวจสอบว่า dormId มีอยู่ในฐานข้อมูลหรือไม่
        if (!dormitoryRepository.existsByDormId(favoriteDTO.getDormId())) {
            throw new ResourceNotFoundException("Dorm id " + favoriteDTO.getDormId() + " not exited!!!");
        }
        // ตรวจสอบว่า userId มีอยู่ในฐานข้อมูลหรือไม่
        if (!userRepository.existsByUserId(favoriteDTO.getUserId())) {
            throw new ResourceNotFoundException("User id " + favoriteDTO.getUserId() + " not exited!!!");
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
}
