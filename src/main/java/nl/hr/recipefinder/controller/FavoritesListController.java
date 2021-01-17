package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.FavoritesListRequestDto;
import nl.hr.recipefinder.model.dto.FavoritesListResponseDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.entity.FavoritesList;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpBadRequestError;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpUnauthorizedException;
import nl.hr.recipefinder.service.FavoritesListService;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequiredArgsConstructor
@RequestMapping()
public class FavoritesListController {
  private final SessionService sessionService;
  private final FavoritesListService favoritesListService;
  private final RecipeService recipeService;
  private final ModelMapper modelMapper;


  @GetMapping("/user/{userId}/favorites")
  public ResponseEntity<List<FavoritesList>> getAllByUser(@PathVariable Long userId) {
    List<FavoritesList> favoritesLists = favoritesListService.findAllByUserId(userId);
    return new ResponseEntity<>(favoritesLists, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/favorites/{favoritesListId}")
  public ResponseEntity<FavoritesList> getSingleById(@PathVariable("userId") Long userId, @PathVariable("favoritesListId") Long favoritesListId) {
    Optional<FavoritesList> favoritesList = favoritesListService.findById(favoritesListId);

    if (favoritesList.isEmpty()) throw new HttpNotFoundError();

    return new ResponseEntity<>(favoritesList.get(), HttpStatus.OK);
  }

  @PatchMapping("/user/{userId}/favorites/{favoritesListId}")
  public ResponseEntity<FavoritesListResponseDto> addFavorite(@RequestBody RecipeDto recipeDto, @PathVariable("userId") Long userId, @PathVariable("favoritesListId") Long favoritesListId) {
    try {
      User activeUser = sessionService.getAuthenticatedUser();

      if (!activeUser.getId().equals(userId)) {
        throw new HttpUnauthorizedException();
      }

      Optional<FavoritesList> foundFavoritesList = favoritesListService.findById(favoritesListId);
      Optional<Recipe> foundRecipe = recipeService.findById(recipeDto.getId());
      if (foundFavoritesList.isEmpty() || foundRecipe.isEmpty()) {
        throw new HttpNotFoundError();
      }

      FavoritesList favoritesList = foundFavoritesList.get();
      favoritesList.getRecipes().add(foundRecipe.get());
      FavoritesList savedFavoritesList = favoritesListService.save(favoritesList);
      FavoritesListResponseDto favoritesListResponseDto = modelMapper.map(savedFavoritesList, FavoritesListResponseDto.class);

      return new ResponseEntity<>(favoritesListResponseDto, HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      throw new HttpBadRequestError(e);
    }
  }

  @PostMapping("/user/{userId}/favorites")
  public ResponseEntity<FavoritesListResponseDto> createFavoritesList(@RequestBody FavoritesListRequestDto favoritesListDto, @PathVariable("userId") Long userId) {
    try {
      User activeUser = sessionService.getAuthenticatedUser();

      if (!activeUser.getId().equals(userId)) {
        throw new HttpUnauthorizedException();
      }

      FavoritesList favoritesList = modelMapper.map(favoritesListDto, FavoritesList.class);
      favoritesList.setUser(activeUser);
      favoritesList.setRecipes(new ArrayList<>());
      FavoritesList savedFavoritesList = favoritesListService.save(favoritesList);
      FavoritesListResponseDto favoritesListResponseDto = modelMapper.map(savedFavoritesList, FavoritesListResponseDto.class);

      return new ResponseEntity<>(favoritesListResponseDto, HttpStatus.CREATED);
    } catch (ConstraintViolationException e) {
      throw new HttpBadRequestError(e);
    }
  }

  @GetMapping("/favorites")
  public ResponseEntity<List<FavoritesListResponseDto>> getAll() {
    List<FavoritesList> favoritesLists = favoritesListService.findAll();
    List<FavoritesListResponseDto> favoritesListsDtos = new ArrayList<>();
    for(FavoritesList favoritesList : favoritesLists) {
      favoritesListsDtos.add(modelMapper.map(favoritesList, FavoritesListResponseDto.class));
    }

    return new ResponseEntity<>(favoritesListsDtos, HttpStatus.OK);
  }
}
