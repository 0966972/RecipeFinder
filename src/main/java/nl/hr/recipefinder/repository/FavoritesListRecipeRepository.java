package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.FavoritesListRecipe;
import nl.hr.recipefinder.model.entity.FavoritesListRecipeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesListRecipeRepository extends JpaRepository<FavoritesListRecipe, FavoritesListRecipeKey> {
}
