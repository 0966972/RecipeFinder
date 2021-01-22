package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Step extends BaseEntity {
  public Integer number;
  public String details;

  @ManyToOne(fetch = FetchType.LAZY)
  private Recipe recipe;
}
