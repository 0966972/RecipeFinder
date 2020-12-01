package nl.hr.recipefinder.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Picture extends BaseEntity {
  private String name;
  private Byte[] content;
  @ManyToOne
  @JoinColumn(name = "recipe_id", nullable = false)
  private Recipe recipe;
}
