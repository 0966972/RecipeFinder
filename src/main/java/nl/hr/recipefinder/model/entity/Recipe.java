package nl.hr.recipefinder.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Recipe extends BaseEntity {
  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;
  @Column(columnDefinition = "TEXT")
  private String instructions;
  private Integer servings;

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

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "step_id")
  public List<Step> steps = new ArrayList<>();
}
