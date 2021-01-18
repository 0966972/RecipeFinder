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
public class FavoritesListRecipeKey implements Serializable {
  @Column(name = "favoriteslist_id")
  Long favoritesListId;

  @Column(name = "recipe_id")
  Long recipeId;
}
