package nl.hr.recipefinder.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends BaseEntity {
  public enum State {PENDING, ACCEPTED, REFUSED}

  private String name;

  @Enumerated(EnumType.STRING)
  private State acceptedState;

  @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
  private List<RecipeIngredient> recipes;
}
