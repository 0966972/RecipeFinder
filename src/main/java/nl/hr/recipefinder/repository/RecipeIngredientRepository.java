package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.RecipeIngredient;
import nl.hr.recipefinder.model.entity.RecipeIngredientKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientKey> {
}
