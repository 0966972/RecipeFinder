package nl.hr.recipefinder.service;

import nl.hr.recipefinder.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
  private IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }
}
