package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
