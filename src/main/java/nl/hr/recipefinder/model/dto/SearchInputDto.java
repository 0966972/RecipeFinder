package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.OptionalInt;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchInputDto {
  private String searchInput;
  private String[] ingredients;
  private OptionalInt minimumScore;
}
