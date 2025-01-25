package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateFavService;
import sit.int371.capstoneproject.dtos.FavDormDTO;
import sit.int371.capstoneproject.dtos.FavoriteDTO;
import sit.int371.capstoneproject.entities.Favorite;
import sit.int371.capstoneproject.services.FavoriteService;


import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://127.0.0.1:5173","http://cp24kk2.sit.kmutt.ac.th","https://kmutt-compare.sit.kmutt.ac.th"})
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
    public List<FavDormDTO> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }

    // Get Favorite By id
    @GetMapping("/{id}")
    public FavDormDTO getFavById(@PathVariable Integer id) {
        return favoriteService.getFavById(id);
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
        return favoriteService.updateFav(id, favoriteDTO);
    }

    // Delete Favorite
    @DeleteMapping("/dorm/{dormId}")
    public ResponseEntity<String> deletedFavByDormId(@PathVariable Integer dormId){
        String message = favoriteService.deleteFavByDormId(dormId);
        return ResponseEntity.ok(message);
    }
}
