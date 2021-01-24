package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {
  public enum State {PENDING, ACCEPTED, REFUSED}
  private long id;
  private String name;
  private State acceptedState;
  private List<RecipeIngredientResponseDto> recipes;
}
