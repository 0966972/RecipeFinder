package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  Optional<Ingredient> findByName(String names);
  List<Ingredient> findByNameContainingIgnoreCaseAndAcceptedStateEquals(String name, Ingredient.State acceptedState);
  List<Ingredient> findByAcceptedState(Ingredient.State acceptedState);
}
