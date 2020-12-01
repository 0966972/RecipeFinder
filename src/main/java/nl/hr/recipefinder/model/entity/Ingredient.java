package nl.hr.recipefinder.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Ingredient extends BaseEntity {
  private String naam;
  @ManyToMany(mappedBy = "ingredients")
  private Set<Recipe> Recipes = new HashSet<>();
//    https://www.baeldung.com/hibernate-many-to-many
}
