package nl.hr.recipefinder.model.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends BaseEntity {
  private String name;

  @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
  private List<RecipeIngredient> recipes = new ArrayList<>();
}
