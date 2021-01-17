package nl.hr.recipefinder.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReportKey implements Serializable {
  @Column(name = "reporting_user_id")
  Long reportingUserId;

  @Column(name = "reported_user_id")
  Long reportedUserId;
}
