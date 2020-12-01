package nl.hr.recipefinder.service;

import nl.hr.recipefinder.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
  private RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }
}
