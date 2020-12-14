package nl.hr.recipefinder.model.dto;

import lombok.Getter;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Ingredient;
import nl.hr.recipefinder.model.entity.Picture;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class StepDto {
  private Integer number;
  private String details;
}
