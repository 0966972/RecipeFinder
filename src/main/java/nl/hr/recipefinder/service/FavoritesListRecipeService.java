package nl.hr.recipefinder.service;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.entity.FavoritesListRecipe;
import nl.hr.recipefinder.repository.FavoritesListRecipeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesListRecipeService {
  private final FavoritesListRecipeRepository favoritesListRecipeRepository;

  public FavoritesListRecipe save(FavoritesListRecipe favoritesListRecipe){
    return favoritesListRecipeRepository.save(favoritesListRecipe);
  }
}
