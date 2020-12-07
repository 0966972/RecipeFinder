package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hr.recipefinder.security.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private long id;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

}
