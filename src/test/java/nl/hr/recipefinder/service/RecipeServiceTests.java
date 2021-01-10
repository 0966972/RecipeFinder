package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RecipeServiceTests {

  @Autowired
  @InjectMocks
  RecipeService recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findById_whenRecipeExists_thenReturnsRecipe(){
    // arrange
    long input = 1;
    Optional<Recipe> recipe = Optional.of(new Recipe());
    when(recipeRepository.findById(input)).thenReturn(recipe);

    // act
    Optional<Recipe> receivedRecipe = recipeService.findById(input);

    // assert
    assertEquals(recipe, receivedRecipe);
  }

  @Test
  public void findById_whenRecipeNotExists_thenReturnsEmpty(){
    // arrange
    long input = 1;
    Optional<Recipe> recipe = Optional.empty();
    when(recipeRepository.findById(input)).thenReturn(recipe);

    // act
    Optional<Recipe> receivedRecipe = recipeService.findById(input);

    // assert
    assertEquals(recipe, receivedRecipe);
  }
}
