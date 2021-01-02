package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  List<Ingredient> findByNameIn(List<String> names);
  List<Ingredient> findByNameContainingIgnoreCase(String name);
}
