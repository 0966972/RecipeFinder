package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
  private RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<Recipe> findAll(){
    return recipeRepository.findAll();
  }

  public void save(Recipe recipe){
    recipeRepository.save(recipe);
  }
}
