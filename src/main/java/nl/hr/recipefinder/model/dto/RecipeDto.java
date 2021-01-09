package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
  private long id;
  private String name;
  private String description;
  private Integer preparationTime;
  private String instructions;
  private Integer servings;

  public UserResponseDto user;

  private List<RecipeIngredientDto> ingredients = new ArrayList<>();

  private List<Picture> pictures = new ArrayList<>();

  private List<StepDto> steps = new ArrayList<>();

  private List<ReviewResponseDto> reviews = new ArrayList<>();
}
