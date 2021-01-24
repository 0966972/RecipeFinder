package nl.hr.recipefinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hr.recipefinder.RecipeFinderApplication;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.dto.UserResponseDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class})
public class RecipeControllerIntegrationTests {

  @Autowired
  RecipeService recipeService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getRecipeReturnsOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe"))
      .andExpect(status().isOk());

    Optional<Recipe> recipe = recipeService.findById(1L);

    assertThat(recipe.isPresent()).isEqualTo(true);
  }

  @Test
  void createRecipeReturnsCreated() throws Exception {
    String description = "This chicken is " + Math.random() * 100 + " times tastier than the next.";
    RecipeDto recipeDto = new RecipeDto(1, "Tasty Chicken", description, 25,
      "Boil the chicken", 2, new UserResponseDto(), List.of(), List.of(), List.of(), List.of());

    String string = objectMapper.writeValueAsString(recipeDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
      .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
      .contentType("application/json")
      .content(string))
      .andExpect(status().isCreated());

    List<Recipe> recipes = recipeService.findRecipesByNameOrDescription(description);
    assertThat(recipes).anyMatch(element -> element.getDescription().equals(description));
  }
}
