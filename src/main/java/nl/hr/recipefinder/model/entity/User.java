package nl.hr.recipefinder.model.entity;

import lombok.*;
import nl.hr.recipefinder.security.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;



@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

  @Column(unique = true)
  public String username;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

}

