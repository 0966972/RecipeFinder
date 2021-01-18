package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.RecipeFinderApplication;
import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.dto.UserResponseDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerError;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class})
public class RecipeControllerTests {

  @Autowired
  RecipeController recipeController;

  @MockBean
  RecipeService recipeService;

  @MockBean
  SessionService sessionService;

  @MockBean
  ModelMapper modelMapper;

  @Test
  void getRecipes_whenRecipesPresent_returnRecipes() {
    // arrange
    List<Recipe> recipes = new ArrayList<>();
    Recipe recipe = new Recipe();
    ListedRecipeDto recipeDto = new ListedRecipeDto();
    recipes.add(recipe);
    Mockito.when(recipeService.getRecipes()).thenReturn(recipes);
    Mockito.when(modelMapper.map(recipe, ListedRecipeDto.class)).thenReturn(recipeDto);

    // act
    ResponseEntity<List<ListedRecipeDto>> result = recipeController.getRecipes();
    List<ListedRecipeDto> list = result.getBody();

    // assert
    assertThat(recipeDto).isIn(list);
    verify(recipeService, times(1)).getRecipes();
    verify(modelMapper, times(1)).map(recipe, ListedRecipeDto.class);
  }

  @Test
  void recipe_whenRecipePresent_returnRecipe() {
    // arrange
    long input = 1;
    Optional<Recipe> recipe = Optional.of(new Recipe());
    RecipeDto recipeDto = new RecipeDto();
    Mockito.when(recipeService.findById(input)).thenReturn(recipe);
    Mockito.when(modelMapper.map(recipe.get(), RecipeDto.class)).thenReturn(recipeDto);

    // act
    ResponseEntity<RecipeDto> response = recipeController.getRecipe(input);

    // assert
    assertThat(response.getBody()).isEqualTo(recipeDto);
    verify(recipeService, times(1)).findById(input);
    verify(modelMapper, times(1)).map(recipe.get(), RecipeDto.class);
  }

  @Test
  void recipe_whenRecipeNotPresent_then404IsReceived() {
    // arrange
    long input = 1;
    Optional<Recipe> recipe = Optional.empty();
    Mockito.when(recipeService.findById(input)).thenReturn(recipe);

    try {
      // act
      recipeController.getRecipe(input);
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpNotFoundError.class);
    }

    verify(recipeService, times(1)).findById(input);
  }

  @Test
  void createRecipe_whenRecipeCreated_then201IsReceived(){
    // arrange
    Recipe recipe = new Recipe("Tasty Chicken", "Very tasty chicken", 25,
      "Boil the chicken" ,2 , List.of(), List.of(), List.of(), List.of(), List.of(), new User("a", "a", Role.USER));
    RecipeDto recipeDto = new RecipeDto(1, "Tasty Chicken", "Very tasty chicken", 25,
      "Boil the chicken" ,2 , new UserResponseDto(), List.of(), List.of(), List.of(), List.of());
    Mockito.when(sessionService.getAuthenticatedUser()).thenReturn(new User("a", "a", Role.USER));
    Mockito.when(modelMapper.map(recipeDto, Recipe.class)).thenReturn(recipe);

    // act
    ResponseEntity<Recipe> response = recipeController.createRecipe(recipeDto);

    //assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    verify(sessionService, times(1)).getAuthenticatedUser();
    verify(modelMapper, times(1)).map(recipeDto, Recipe.class);
  }
}
