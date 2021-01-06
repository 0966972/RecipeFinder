package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.AdminIngredientDto;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/admin")
public class AdminController {
  private final IngredientService ingredientService;
  private final ModelMapper modelMapper;

  @Autowired
  public AdminController(
    IngredientService ingredientService,
    ModelMapper modelMapper
  ) {
    this.ingredientService = ingredientService;
    this.modelMapper = modelMapper;
  }


  @PatchMapping("/ingredients")
  public Ingredient updateIngredientState(@RequestBody AdminIngredientDto ingredient) {
    return ingredientService.update(modelMapper.map(ingredient, Ingredient.class));
  }


  @GetMapping("/ingredients/refused")
  public List<AdminIngredientDto> getRefusedIngredients() {
    List<Ingredient> ingredients = ingredientService.getIngredientsBySate(Ingredient.State.REFUSED);
    return ingredients.stream().map((it) ->
      modelMapper.map(it, AdminIngredientDto.class)
    ).collect(Collectors.toList());
  }


  @GetMapping("/ingredients/pending")
  public List<AdminIngredientDto> getPendingIngredients() {
    List<Ingredient> ingredients = ingredientService.getIngredientsBySate(Ingredient.State.PENDING);
    return ingredients.stream().map((it) ->
      modelMapper.map(it, AdminIngredientDto.class)
    ).collect(Collectors.toList());
  }
}
