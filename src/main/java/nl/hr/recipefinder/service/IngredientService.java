package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public List<Ingredient> findOrCreateIngredients(List<Ingredient> ingredients) {
    List<Ingredient> existingIngredients = ingredientRepository.findByNameIn(
      ingredients.stream()
        .map(Ingredient::getName)
        .collect(Collectors.toList())
    );
    List<Ingredient> ingredientsToCreate =
      ingredients.stream().filter((it) ->
        existingIngredients.stream().noneMatch((other) ->
          other.getName().equals(it.getName())
        )
      ).collect(Collectors.toList());

    List<Ingredient> createdIngredients = ingredientRepository.saveAll(ingredientsToCreate);

    ArrayList<Ingredient> finalList = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      boolean added = false;
      for (Ingredient createdIngredient : createdIngredients) {
        if (ingredient.getName().equals(createdIngredient.getName())) {
          finalList.add(createdIngredient);
          added = true;
          break;
        }
      }

      if (added)
        continue;

      for (Ingredient existingIngredient : existingIngredients) {
        if (ingredient.getName().equals(existingIngredient.getName())) {
          finalList.add(existingIngredient);
          added = true;
          break;
        }
      }

      if (!added) throw new IllegalStateException("Should be created by now but isn't.");
    }

    return finalList;
  }


  public List<Ingredient> findIngredientsByName(String searchInput) {
    return ingredientRepository.findByNameContainingIgnoreCase(searchInput);
  }


}
