package nl.hr.recipefinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hr.recipefinder.model.entity.Picture;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
  @Size(max = 5)
  private Integer score;

  @Size(min = 1, max = 500)
  private String message;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Picture> pictures;
}

