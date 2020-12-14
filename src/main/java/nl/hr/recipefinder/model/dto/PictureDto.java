package nl.hr.recipefinder.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Recipe;

import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class PictureDto {
  private String name;
  private String type;
  private byte[] content;

  private Recipe recipe;

}
