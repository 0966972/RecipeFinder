package nl.hr.recipefinder.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesList extends BaseEntity {
  @Column(columnDefinition = "TEXT")
  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  public User user;

  @OneToMany(mappedBy = "favoritesList")
  private List<FavoritesListRecipe> recipes = new ArrayList<>();
}
