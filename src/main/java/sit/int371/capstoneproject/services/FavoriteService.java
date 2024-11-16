package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.FavoriteDTO;
import sit.int371.capstoneproject.entities.Favorite;
import sit.int371.capstoneproject.repositories.FavoriteRepository;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ModelMapper modelMapper;


    //Method -find All fav
    public List<Favorite> getAllFavorite(){
        return favoriteRepository.findAll();
    }

    //Method -find favorite by id
    public Favorite getFavById(Integer id){
        return favoriteRepository.findByFavId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite id " + id + " not found !!!"));
    }

    //Method -create favorite
    public FavoriteDTO createFav(Favorite favorite){
        Favorite addFav = new Favorite();
        addFav.setFavId(favorite.getFavId());
        addFav.setDormId(favorite.getDormId());
        addFav.setUserId(favorite.getUserId());
        return modelMapper.map(favoriteRepository.save(favorite), FavoriteDTO.class);
    }

    //Method -update favorite
    public FavoriteDTO updateFav(Integer id, FavoriteDTO favoriteDTO){
        Favorite exitsFav = favoriteRepository.findByFavId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + " does not exited!!! "));
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
