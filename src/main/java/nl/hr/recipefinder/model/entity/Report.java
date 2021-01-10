package nl.hr.recipefinder.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity {
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reporting_user_id", nullable = false)
  private User reportingUser;

  @Size(min = 1, max = 500)
  private String message;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reported_user_id", nullable = false)
  private User reportedUser;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id", nullable = false)
  private Recipe reportedRecipe;

}
