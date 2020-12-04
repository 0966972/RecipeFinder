package nl.hr.recipefinder.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ListedRecipeDto {
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;
}
