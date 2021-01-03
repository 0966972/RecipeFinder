package nl.hr.recipefinder.model.dto;

import lombok.*;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListedRecipeDto {
  private Long id;
  private String name;

  private String description;
  private Integer preparationTime;
  private Integer servings;

  private List<Picture> pictures = new ArrayList<>();
}
