package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
  private long id;
  private ReportResponseUserDto reportingUser;
  private String message;
  private ReportResponseUserDto reportedUser;
  private ReportResponseRecipeDto reportedRecipe;
}
