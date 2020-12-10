package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends BaseEntity {
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "ingredient")
  private Set<RecipeIngredient> recipes = new HashSet<>();
}
