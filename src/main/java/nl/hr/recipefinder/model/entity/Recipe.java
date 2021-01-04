package nl.hr.recipefinder.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recipe extends BaseEntity {
  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;
  private Integer preparationTime;
  @Column(columnDefinition = "TEXT")
  private String instructions;
  private Integer servings;

  @OneToMany(mappedBy = "recipe")
  private List<RecipeIngredient> ingredients;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<Picture> pictures = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "step_id")
  public List<Step> steps = new ArrayList<>();

  @OneToMany(mappedBy="recipe", cascade = CascadeType.ALL)
  List<Review> reviews;
}
