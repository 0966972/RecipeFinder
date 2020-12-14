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

  private List<Ingredient> ingredients = new ArrayList<>();

  private List<Picture> pictures = new ArrayList<>();
}
