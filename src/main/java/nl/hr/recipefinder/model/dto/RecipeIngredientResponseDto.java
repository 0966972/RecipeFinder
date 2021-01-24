package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.RecipeIngredientKey;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientResponseDto {
  private RecipeIngredientKey id;
  private LocalDateTime createdOn;
  private LocalDateTime lastModifiedOn;
  private String measurement;
}
