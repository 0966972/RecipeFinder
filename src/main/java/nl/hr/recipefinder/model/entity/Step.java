package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Step extends BaseEntity {
  public Integer number;
  public String details;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Recipe recipe;
}
