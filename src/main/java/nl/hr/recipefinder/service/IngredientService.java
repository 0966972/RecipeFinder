package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.dto.PictureDto;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public Ingredient update(Ingredient ingredient) {
    return ingredientRepository.save(ingredient);
  }

  public List<Ingredient> findOrCreateIngredients(List<Ingredient> ingredients) {
    return ingredients.stream()
      .map(ingredient -> ingredientRepository.findByName(ingredient.getName())
        .orElseGet( () -> saveNewIngredient(ingredient)))
      .collect(Collectors.toList());
  }

  private Ingredient saveNewIngredient(Ingredient ingredient){
    ingredient.setAcceptedState(Ingredient.State.PENDING);
    return ingredientRepository.save(ingredient);
  }

  public List<Ingredient> getIngredientsBySate(Ingredient.State state) {
    return ingredientRepository.findByAcceptedState(state);
  }


  public List<Ingredient> findIngredientsByName(String searchInput) {
    return ingredientRepository.findByNameContainingIgnoreCaseAndAcceptedStateEquals(searchInput, Ingredient.State.ACCEPTED);
  }


}
