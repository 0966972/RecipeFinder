package nl.hr.recipefinder.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RecipeIngredientKey implements Serializable {
  @Column(name = "recipe_id")
  Long recipeId;

  @Column(name = "ingredient_id")
  Long ingredientId;
}
