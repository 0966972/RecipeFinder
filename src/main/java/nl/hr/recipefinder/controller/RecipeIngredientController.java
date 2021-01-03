package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.RecipeIngredientDto;
import nl.hr.recipefinder.model.entity.RecipeIngredient;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictError;
import nl.hr.recipefinder.service.RecipeIngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/recipeIngredient")
public class RecipeIngredientController {

  private final RecipeIngredientService recipeIngredientService;
  final ModelMapper modelMapper;

  @Autowired
  public RecipeIngredientController(
    RecipeIngredientService recipeIngredientService,
    ModelMapper modelMapper
  ) {
    this.recipeIngredientService = recipeIngredientService;
    this.modelMapper = modelMapper;
  }

  @PostMapping()
  public ResponseEntity<List<RecipeIngredient>> createIngredients(@RequestBody List<RecipeIngredientDto> recipeIngredients) {
    try {
      List<RecipeIngredient> mappedRecipeIngredients =
        recipeIngredients.stream()
          .map((it) -> modelMapper.map(it, RecipeIngredient.class))
          .collect(Collectors.toList());
      List<RecipeIngredient> savedRecipeIngredients = recipeIngredientService.saveAll(mappedRecipeIngredients);

      return new ResponseEntity<>(savedRecipeIngredients, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictError(e);
    }
  }
}
