package nl.hr.recipefinder.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Report {

  @EmbeddedId
  private ReportKey id;

  @CreationTimestamp
  private LocalDateTime createdOn;

  @UpdateTimestamp
  private LocalDateTime lastModifiedOn;

  @Size(min = 1, max = 500)
  private String message;

  @OneToOne(fetch = FetchType.EAGER)
  @MapsId("reportingUserId")
  @JoinColumn(name = "reporting_user_id", nullable = false)
  private User reportingUser;

  @OneToOne(fetch = FetchType.EAGER)
  @MapsId("reportedUserId")
  @JoinColumn(name = "reported_user_id", nullable = false)
  private User reportedUser;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id", nullable = false)
  private Recipe reportedRecipe;

}
