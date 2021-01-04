package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public Ingredient update(Ingredient ingredient) {
    return ingredientRepository.save(ingredient);
  }

  public List<Ingredient> findOrCreateIngredients(List<Ingredient> ingredientsToCreate) {
    ArrayList<Ingredient> finalIngredients = new ArrayList<>();
    for (Ingredient ingredientToCreate : ingredientsToCreate) {
      Optional<Ingredient> existingIngredient = ingredientRepository.findByName(ingredientToCreate.getName());

      if (existingIngredient.isPresent()) {
        finalIngredients.add(existingIngredient.get());
        continue;
      }

      ingredientToCreate.setAcceptedState(Ingredient.State.PENDING);
      finalIngredients.add(ingredientRepository.save(ingredientToCreate));
    }

    return finalIngredients;
  }

  public List<Ingredient> getIngredientsBySate(Ingredient.State state) {
    return ingredientRepository.findByAcceptedState(state);
  }


  public List<Ingredient> findIngredientsByName(String searchInput) {
    return ingredientRepository.findByNameContainingIgnoreCaseAndAcceptedStateEquals(searchInput, Ingredient.State.ACCEPTED);
  }


}
