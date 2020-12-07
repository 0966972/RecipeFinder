package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
  public List<ListedRecipeDto> getRecipes() {
    List<Recipe> recipes = recipeService.getRecipes();

    return recipes.stream()
      .map((it) -> modelMapper.map(it, ListedRecipeDto.class))
      .collect(Collectors.toList());
  }


  @GetMapping("/search/{searchInput}")
  public List<ListedRecipeDto> searchRecipes(@PathVariable String searchInput) {
    List<Recipe> recipes = recipeService.findRecipesByNameAndDescription(searchInput, searchInput);

    return recipes.stream()
      .map((it) -> modelMapper.map(it, ListedRecipeDto.class))
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public RecipeDto Recipe(@PathVariable("id") Long id) {
    Optional<Recipe> recipe = recipeService.findById(id);
    return modelMapper.map(recipe.get(), RecipeDto.class);
  }

  @PostMapping()
  public boolean createRecipe(@RequestBody RecipeDto recipedto) {
    Recipe mappedRecipe = modelMapper.map(recipedto, Recipe.class);
    recipeService.save(mappedRecipe);
    return true;
  }
}
