package nl.hr.recipefinder.controller;

import io.swagger.models.Response;
import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictError;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.model.httpexception.serverError.HttpInternalServerError;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/recipe")
public class RecipeController {

  private final RecipeService recipeService;
  final ModelMapper modelMapper;

  @Autowired
  public RecipeController(RecipeService recipeService, ModelMapper modelMapper) {
    this.recipeService = recipeService;
    this.modelMapper = modelMapper;
  }

  @GetMapping()
  public ResponseEntity<List<ListedRecipeDto>> getRecipes() {
    List<Recipe> recipes = recipeService.getRecipes();
    try {
      return new ResponseEntity<>(recipes.stream()
        .map((it) -> modelMapper.map(it, ListedRecipeDto.class))
        .collect(Collectors.toList()), HttpStatus.OK);
    } catch (Exception e) {
      throw new HttpInternalServerError(e);
    }
  }

  @GetMapping("/search/{searchInput}")
  public ResponseEntity<List<ListedRecipeDto>> searchRecipes(@PathVariable String searchInput) {
    try {
      List<Recipe> recipes = recipeService.findRecipesByNameAndDescription(searchInput, searchInput);

      return new ResponseEntity<>(recipes.stream()
        .map((it) -> modelMapper.map(it, ListedRecipeDto.class))
        .collect(Collectors.toList()), HttpStatus.OK);
    } catch (Exception e) {
      throw new HttpInternalServerError(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecipeDto> Recipe(@PathVariable("id") Long id) {
    Optional<Recipe> recipe = recipeService.findById(id);
    if (recipe.isPresent()) return new ResponseEntity<>(modelMapper.map(recipe.get(), RecipeDto.class), HttpStatus.OK);
    else throw new HttpNotFoundError();
  }

  @PostMapping()
  public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDto recipedto) {
    try{
      Recipe mappedRecipe = modelMapper.map(recipedto, Recipe.class);
      Recipe savedRecipe = recipeService.save(mappedRecipe);

      return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }
    catch (DataIntegrityViolationException e){
      throw new HttpConflictError(e);
    }
  }
}
