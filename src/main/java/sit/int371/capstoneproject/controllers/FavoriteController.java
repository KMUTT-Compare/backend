package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateFavService;
import sit.int371.capstoneproject.dtos.FavoriteDTO;
import sit.int371.capstoneproject.entities.Favorite;
import sit.int371.capstoneproject.services.FavoriteService;


import java.util.List;


@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private SequenceGenerateFavService sequenceGenerateFavService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get All Favorites
    @GetMapping("")
    public List<FavoriteDTO> getAllFavDTO(){
        List<Favorite> favList = favoriteService.getAllFavorite();
        return listMapper.mapList(favList, FavoriteDTO.class, modelMapper);
    }

    // Get Favorite By id
    @GetMapping("/{id}")
    public FavoriteDTO getFavDTO(@PathVariable Integer id) {
        return modelMapper.map(favoriteService.getFavById(id), FavoriteDTO.class);
    }

    @PostMapping()
    public FavoriteDTO createdFav(@Valid @RequestBody FavoriteDTO favoriteDTO){
        //generate dormitory id
        favoriteDTO.setFavId((int) sequenceGenerateFavService.generateSequence(Favorite.SEQUENCE_NAME));
        Favorite favorite = modelMapper.map(favoriteDTO, Favorite.class);
        return favoriteService.createFav(favorite);
    }

    @PutMapping("/{id}")
    public FavoriteDTO updatedFav(@PathVariable Integer id, @Valid @RequestBody FavoriteDTO favoriteDTO){
        //generate dormitory id
        favoriteDTO.setFavId((int) sequenceGenerateFavService.generateSequence(Favorite.SEQUENCE_NAME));
        return favoriteService.updateFav(id, favoriteDTO);
    }

    // Delete Staff
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedFav(@PathVariable Integer id){
        String message = favoriteService.deleteFav(id);
        return ResponseEntity.ok(message);
    }
}
