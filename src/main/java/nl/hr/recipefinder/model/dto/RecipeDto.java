package nl.hr.recipefinder.model.dto;

import lombok.*;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
  private String name;
  private String description;
  private String instructions;
  private Integer servings;

  private Set<Ingredient> ingredients = new HashSet<>();

  private Set<Picture> pictures = new HashSet<>();
}
