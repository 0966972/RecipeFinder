package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.UserDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

  private RecipeService recipeService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  //TODO NAAR DTO
  @GetMapping()
  List<Recipe> all()
  {
    return recipeService.findAll();
  }
  //TODO NAAR DTO
  @PostMapping()
  public String createRecipe(@RequestBody Recipe recipe) {
    recipeService.save(recipe);
    return recipe.toString();
  }
}
