package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.IngredientDto;
import nl.hr.recipefinder.model.dto.IngredientResponseDto;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/ingredient")
public class IngredientController {
  private final IngredientService ingredientService;
  private final ModelMapper modelMapper;

  @Transactional
  @PostMapping()
  public List<IngredientResponseDto> createIngredients(@RequestBody List<IngredientDto> ingredientDtos) {
    List<Ingredient> mappedIngredients = ingredientDtos.stream()
      .map(it -> new Ingredient(it.getName(), Ingredient.State.PENDING, List.of()))
      .collect(Collectors.toList());

    return ingredientService.findOrCreateIngredients(mappedIngredients).stream()
      .map(it -> modelMapper.map(it, IngredientResponseDto.class))
      .collect(Collectors.toList());
  }

  @GetMapping("/search/{searchInput}")
  public List<IngredientDto> searchIngredients(@PathVariable String searchInput) {
    List<Ingredient> ingredients = ingredientService.findIngredientsByName(searchInput);

    return ingredients
      .stream()
      .map(it -> modelMapper.map(it, IngredientDto.class))
      .collect(Collectors.toList());
  }
}
