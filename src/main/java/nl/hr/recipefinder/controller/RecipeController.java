package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictError;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.model.httpexception.serverError.HttpInternalServerError;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
  private final SessionService sessionService;

  @Autowired
  public RecipeController(
    RecipeService recipeService,
    ModelMapper modelMapper,
    SessionService sessionService
  ) {
    this.recipeService = recipeService;
    this.modelMapper = modelMapper;
    this.sessionService = sessionService;
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

  @PostMapping(value = {"/search/", "/search/{searchInput}"})
  public ResponseEntity<List<ListedRecipeDto>> searchRecipes(@PathVariable(required = false) String searchInput, @RequestBody String[] ingredients) {
    List<Recipe> recipes;
    if (searchInput != null) {
      recipes = recipeService.findRecipesByNameOrDescription(searchInput);
    } else {
      recipes = recipeService.getRecipes();
    }
    try {

      ArrayList<Recipe> foundRecipes = new ArrayList<>();
      for (Recipe recipe : recipes) {
        boolean match = true;
        for (String ingredient : ingredients) {
          if (recipe.getIngredients().stream().noneMatch((it) -> it.getIngredient().getName().equalsIgnoreCase(ingredient))) {
            match = false;
            break;
          }
        }

        if (match)
          foundRecipes.add(recipe);
      }

      return new ResponseEntity<>(foundRecipes.stream()
        .map((it) -> modelMapper.map(it, ListedRecipeDto.class))
        .collect(Collectors.toList()), HttpStatus.OK);
    } catch (Exception e) {
      throw new HttpInternalServerError(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecipeDto> Recipe(@PathVariable("id") Long id) {
    Optional<Recipe> recipe = recipeService.findById(id);

    if(!recipe.isPresent()){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    var recipeDto = modelMapper.map(recipe.get(), RecipeDto.class);

    if(recipe.get().user != null){
      recipeDto.creator = recipe.get().user.username;
    }

    return new ResponseEntity<>(recipeDto, HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDto recipedto) {
    try {
      User user = sessionService.getAuthenticatedUser();
      Recipe mappedRecipe = modelMapper.map(recipedto, Recipe.class);
      mappedRecipe.user = user;
      Recipe savedRecipe = recipeService.save(mappedRecipe);

      return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictError(e);
    }
  }
}
