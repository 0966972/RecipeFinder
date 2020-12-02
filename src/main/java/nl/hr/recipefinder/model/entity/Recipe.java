package nl.hr.recipefinder.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Recipe extends BaseEntity {
  private String name;
  private String desription;
  private String instructions;
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
    name = "Recipe_Ingredient",
    joinColumns = {@JoinColumn(name = "recipe_id")},
    inverseJoinColumns = {@JoinColumn(name = "ingredient_id")}
  )
  Set<Ingredient> ingredients = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private Set<Picture> pictures = new HashSet<>();
}
