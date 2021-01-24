package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarningResponseDto {
  private Long id;
  private String message;
  private WarningResponseUserDto warnedUser;
}
