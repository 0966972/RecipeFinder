package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.RecipeIngredient;
import nl.hr.recipefinder.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {
  private final RecipeIngredientRepository recipeIngredientRepository;

  public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
    this.recipeIngredientRepository = recipeIngredientRepository;
  }

  public List<RecipeIngredient> saveAll(List<RecipeIngredient> ingredients) {
    return recipeIngredientRepository.saveAll(ingredients);
  }
}
