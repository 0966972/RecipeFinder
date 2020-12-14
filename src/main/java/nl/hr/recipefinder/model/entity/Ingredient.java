package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

  @JsonIgnore
  @OneToMany(mappedBy = "ingredient")
  private List<RecipeIngredient> recipes = new ArrayList<>();
}
