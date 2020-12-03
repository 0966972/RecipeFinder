package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<Recipe> findAll() {
    return recipeRepository.findAll();
  }

  public Optional<Recipe> findById(Long id) {
    return recipeRepository.findById(id);
  }

  public void save(Recipe recipe) {
    recipeRepository.save(recipe);
  }
}
