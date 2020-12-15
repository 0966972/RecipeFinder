package nl.hr.recipefinder.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
  List<Ingredient> ingredients = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<Picture> pictures = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "step_id")
  public List<Step> steps = new ArrayList<>();
}
