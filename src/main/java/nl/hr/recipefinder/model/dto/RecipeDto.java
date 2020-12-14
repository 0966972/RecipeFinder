package nl.hr.recipefinder.model.dto;

import lombok.Getter;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RecipeDto {
  private String name;
  private String description;
  private String instructions;
  private Integer servings;

  private Set<Ingredient> ingredients = new HashSet<>();

  private Set<Picture> pictures = new HashSet<>();

  private List<StepDto> steps = new ArrayList<>();
}
