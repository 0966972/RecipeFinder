package nl.hr.recipefinder.model.dto;

import lombok.Getter;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Recipe;

@Getter
@Setter
public class PictureDto {
  private String name;
  private String type;
  private byte[] content;
  private boolean thumbnail;
  private Recipe recipe;

}
