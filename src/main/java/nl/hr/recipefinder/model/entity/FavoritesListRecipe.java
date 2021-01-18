package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class FavoritesListRecipe {

  @EmbeddedId
  private FavoritesListRecipeKey id;

  @CreationTimestamp
  private LocalDateTime createdOn;

  @UpdateTimestamp
  private LocalDateTime lastModifiedOn;

  @ManyToOne
  @MapsId("recipeId")
  @JoinColumn(name = "recipe_id")
  @JsonBackReference
  private Recipe recipe;

  @ManyToOne
  @MapsId("favoriteslistId")
  @JoinColumn(name = "favoriteslist_id")
  @JsonBackReference
  private FavoritesList favoritesList;
}
