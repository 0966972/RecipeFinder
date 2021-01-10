package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
  private long reportingUserId;
  private String message;
  private long reportedUserId;
  private long reportedRecipeId;
}
