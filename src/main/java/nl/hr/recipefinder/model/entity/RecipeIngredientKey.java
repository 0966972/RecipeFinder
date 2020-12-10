package nl.hr.recipefinder.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Data
@Embeddable
public class RecipeIngredientKey implements Serializable {
  @Column(name = "recipe_id")
  Long recipeId;

  @Column(name = "ingredient_id")
  Long ingredientId;
}
