package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.ReportKey;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
  private ReportKey id;
  private ReportResponseUserDto reportingUser;
  private String message;
  private ReportResponseUserDto reportedUser;
  private ReportResponseRecipeDto reportedRecipe;
}
