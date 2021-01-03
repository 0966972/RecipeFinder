package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.IngredientDto;
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
@RequestMapping("/ingredient")
public class IngredientController {
  private final IngredientService ingredientService;
  private final ModelMapper modelMapper;

  @Autowired
  public IngredientController(IngredientService ingredientService, ModelMapper modelMapper) {
    this.ingredientService = ingredientService;
    this.modelMapper = modelMapper;
  }


  @PostMapping()
  public List<Ingredient> createIngredients(@RequestBody List<IngredientDto> ingredientDtos) {
    List<Ingredient> mappedIngredients = ingredientDtos.stream()
      .map((it) -> modelMapper.map(it, Ingredient.class))
      .collect(Collectors.toList());

    return ingredientService.findOrCreateIngredients(mappedIngredients);
  }



  @GetMapping("/search/{searchInput}")
  public List<Ingredient> searchIngredients(@PathVariable String searchInput) {
    return ingredientService.findIngredientsByName(searchInput);
  }
}
