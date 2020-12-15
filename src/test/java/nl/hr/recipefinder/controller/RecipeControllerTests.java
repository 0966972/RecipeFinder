package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.RecipeFinderApplication;
import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.service.RecipeService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class})
public class RecipeControllerTests {

  @Autowired
  @InjectMocks
  RecipeController recipeController;

  @MockBean
  RecipeService recipeService;

  @MockBean
  ModelMapper modelMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

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
}
