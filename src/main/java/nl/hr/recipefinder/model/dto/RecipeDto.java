package nl.hr.recipefinder.model.dto;

import lombok.*;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
  private String name;
  private String description;
  private Integer preparationTime;
  private String instructions;
  private Integer servings;

  private List<Ingredient> ingredients = new ArrayList<>();

  private List<Picture> pictures = new ArrayList<>();

  private List<StepDto> steps = new ArrayList<>();
}
