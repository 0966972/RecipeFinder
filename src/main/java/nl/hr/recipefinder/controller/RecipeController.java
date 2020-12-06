package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.dto.UserDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

  @GetMapping()
  public List<RecipeDto> all()
  {
    List<Recipe> listRecipe = recipeService.findAll();
    List<RecipeDto> listRecipeDto = new ArrayList<RecipeDto>();
    for(int i = 0; i < listRecipe.size(); i++){
      listRecipeDto.add(modelMapper.map(listRecipe.get(i), RecipeDto.class));
    }
    return listRecipeDto;
  }
  @PostMapping()
  public String createRecipe(@RequestBody RecipeDto recipedto) {
    Recipe mappedRecipe = modelMapper.map(recipedto, Recipe.class);
    recipeService.save(mappedRecipe);
    return mappedRecipe.toString();
  }
}
