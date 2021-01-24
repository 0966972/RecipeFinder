package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.dto.SearchInputDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Review;
import nl.hr.recipefinder.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }


  public List<Recipe> getRecipes() {
    return recipeRepository.findAll();
  }


  public List<Recipe> findRecipesByNameOrDescription(String searchInput) {
    return recipeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchInput, searchInput);
  }

  public Optional<Recipe> findById(Long id) {
    return recipeRepository.findById(id);
  }

  public Recipe save(Recipe recipe) {
    return recipeRepository.save(recipe);
  }

  public List<Recipe> findMatches(SearchInputDto searchInput, List<Recipe> recipes) {
    ArrayList<Recipe> foundRecipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      if (checkIfRecipeHasAllSearchedIngredients(searchInput, recipe) && checkIfRecipeScoreIsAboveSearchedScore(searchInput, recipe)) {
        foundRecipes.add(recipe);
      }
    }
    return foundRecipes;
  }

  private boolean checkIfRecipeHasAllSearchedIngredients(SearchInputDto searchInput, Recipe recipe) {
    for (String ingredient : searchInput.getIngredients()) {
      if (recipe.getIngredients().stream().noneMatch(it -> it.getIngredient().getName().equalsIgnoreCase(ingredient))) {
        return false;
      }
    }
    return true;
  }

  private boolean checkIfRecipeScoreIsAboveSearchedScore(SearchInputDto searchInput, Recipe recipe) {
    if (searchInput.getMinimumScore().isEmpty()) return true;

    OptionalDouble averageScore = recipe.getReviews().stream().mapToInt(Review::getScore).average();
    if (averageScore.isEmpty()) return false;

    return averageScore.getAsDouble() >= searchInput.getMinimumScore().getAsInt();
  }

}
