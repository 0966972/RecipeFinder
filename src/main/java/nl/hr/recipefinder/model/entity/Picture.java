package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Picture extends BaseEntity {
  private String name;
  private Byte[] content;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Recipe recipe;
}
