package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.RecipeIngredientDto;
import nl.hr.recipefinder.model.entity.RecipeIngredient;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.service.RecipeIngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/recipeIngredient")
public class RecipeIngredientController {

  private final RecipeIngredientService recipeIngredientService;
  private final ModelMapper modelMapper;

  @Transactional
  @PostMapping()
  public ResponseEntity<List<RecipeIngredient>> createIngredients(@RequestBody List<RecipeIngredientDto> recipeIngredients) {
    try {
      List<RecipeIngredient> mappedRecipeIngredients =
        recipeIngredients.stream()
          .map(it -> modelMapper.map(it, RecipeIngredient.class))
          .collect(Collectors.toList());
      List<RecipeIngredient> savedRecipeIngredients = recipeIngredientService.saveAll(mappedRecipeIngredients);

      return new ResponseEntity<>(savedRecipeIngredients, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictException(e);
    }
  }
}
