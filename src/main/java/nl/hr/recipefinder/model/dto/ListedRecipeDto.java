package nl.hr.recipefinder.model.dto;

import lombok.Getter;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ListedRecipeDto {
  private Long id;
  private String name;

  private String description;
  private Integer servings;

  private Set<Picture> pictures = new HashSet<>();
}
