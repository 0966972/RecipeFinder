package nl.hr.recipefinder.model.dto;

import lombok.*;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.entity.Review;

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
  private Integer servings;

  private List<Picture> pictures = new ArrayList<>();
  private List<Review> reviews = new ArrayList<>();
}
