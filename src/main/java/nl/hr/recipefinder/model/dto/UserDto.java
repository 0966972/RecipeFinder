package nl.hr.recipefinder.model.dto;

import nl.hr.recipefinder.security.Role;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserDto {
  private String username;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

}
