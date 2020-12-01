package nl.hr.recipefinder.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe extends BaseEntity {
  private String name;
  private String desription;
  private String instructions;
  @OneToMany
  private List<Picture> pictures;
}
