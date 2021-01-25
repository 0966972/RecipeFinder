package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.FavoritesListRequestDto;
import nl.hr.recipefinder.model.dto.FavoritesListResponseDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.entity.*;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpBadRequestException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpUnauthorizedException;
import nl.hr.recipefinder.service.AuthenticationService;
import nl.hr.recipefinder.service.FavoritesListRecipeService;
import nl.hr.recipefinder.service.FavoritesListService;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
  private final AuthenticationService authenticationService;
  private final FavoritesListService favoritesListService;
  private final FavoritesListRecipeService favoritesListRecipeService;
  private final RecipeService recipeService;
  private final ModelMapper modelMapper;

  @GetMapping("/user/{userId}/favorites")
  public ResponseEntity<List<FavoritesListResponseDto>> getAllByUser(@PathVariable Long userId) {
    List<FavoritesList> favoritesLists = favoritesListService.findAllByUserId(userId);

    return getListResponseEntity(favoritesLists);
  }

  @GetMapping("/user/{userId}/favorites/{favoritesListId}")
  public ResponseEntity<FavoritesListResponseDto> getSingleById(@PathVariable("userId") Long userId, @PathVariable("favoritesListId") Long favoritesListId) {
    Optional<FavoritesList> favoritesList = favoritesListService.findById(favoritesListId);

    if (favoritesList.isEmpty()) throw new HttpNotFoundException();

    FavoritesListResponseDto favoritesListDto = modelMapper.map(favoritesList.get(), FavoritesListResponseDto.class);
    favoritesListDto.setRecipes(findRelatedRecipesOnDto(favoritesListDto));

    return new ResponseEntity<>(favoritesListDto, HttpStatus.OK);
  }

  @Transactional
  @PatchMapping("/user/{userId}/favorites/{favoritesListId}")
  public ResponseEntity<FavoritesListResponseDto> addFavorite(@RequestBody Long recipeId, @PathVariable("userId") Long userId, @PathVariable("favoritesListId") Long favoritesListId) {
    try {
      User activeUser = authenticationService.getAuthenticatedUser();

      if (!activeUser.getId().equals(userId)) {
        throw new HttpUnauthorizedException();
      }

      Optional<FavoritesList> foundFavoritesList = favoritesListService.findById(favoritesListId);
      Optional<Recipe> foundRecipe = recipeService.findById(recipeId);
      if (foundFavoritesList.isEmpty() || foundRecipe.isEmpty()) {
        throw new HttpNotFoundException();
      }

      FavoritesListRecipe mappedFavoriteRecipe = modelMapper.map(foundRecipe.get(), FavoritesListRecipe.class);
      mappedFavoriteRecipe.setId(new FavoritesListRecipeKey(favoritesListId, foundRecipe.get().getId()));
      favoritesListRecipeService.save(mappedFavoriteRecipe);

      FavoritesList favoritesList = foundFavoritesList.get();
      FavoritesList savedFavoritesList = favoritesListService.save(favoritesList);
      FavoritesListResponseDto favoritesListResponseDto = modelMapper.map(savedFavoritesList, FavoritesListResponseDto.class);

      favoritesListResponseDto.setRecipes(findRelatedRecipesOnDto(favoritesListResponseDto));

      return new ResponseEntity<>(favoritesListResponseDto, HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      throw new HttpBadRequestException(e);
    }
  }

  @Transactional
  @PostMapping("/user/{userId}/favorites")
  public ResponseEntity<FavoritesListResponseDto> createFavoritesList(@RequestBody FavoritesListRequestDto favoritesListDto, @PathVariable("userId") Long userId) {
    try {
      User activeUser = authenticationService.getAuthenticatedUser();

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
      throw new HttpBadRequestException(e);
    }
  }

  @GetMapping("/favorites")
  public ResponseEntity<List<FavoritesListResponseDto>> getAll() {
    List<FavoritesList> favoritesLists = favoritesListService.findAll();

    return getListResponseEntity(favoritesLists);
  }

  private ResponseEntity<List<FavoritesListResponseDto>> getListResponseEntity(List<FavoritesList> favoritesLists) {
    List<FavoritesListResponseDto> favoritesListsDtos = new ArrayList<>();
    for (FavoritesList favoritesList : favoritesLists) {
      favoritesListsDtos.add(modelMapper.map(favoritesList, FavoritesListResponseDto.class));
    }
    for (FavoritesListResponseDto list : favoritesListsDtos) {
      list.setRecipes(findRelatedRecipesOnDto(list));
    }

    return new ResponseEntity<>(favoritesListsDtos, HttpStatus.OK);
  }

  public List<RecipeDto> findRelatedRecipesOnDto(FavoritesListResponseDto favoritesListDto) {
    List<RecipeDto> recipesFoundByRelation = new ArrayList<>();
    for (RecipeDto recipe : favoritesListDto.getRecipes()) {
      Optional<Recipe> foundRelatedRecipe = recipeService.findById(recipe.getId());
      foundRelatedRecipe.ifPresent(value -> recipesFoundByRelation.add(modelMapper.map(value, RecipeDto.class)));
    }
    return recipesFoundByRelation;
  }
}
