package nl.hr.recipefinder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Picture extends BaseEntity {
  private String name;
  private String type;
  @Lob
  private byte[] content;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Recipe recipe;

  public Picture(){}

  public Picture(String name, String type, byte[] content)
  {
    this.name = name;
    this.type = type;
    this.content = content;
  }

  public byte[] getContent() {
    return content;
  }
}
