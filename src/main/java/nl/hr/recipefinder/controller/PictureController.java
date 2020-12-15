package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.PictureDto;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/picture")
public class PictureController {


  private final PictureService pictureService;

  final ModelMapper modelMapper;

  @Autowired
  public PictureController(PictureService pictureService, ModelMapper modelMapper) {
    this.pictureService = pictureService;
    this.modelMapper = modelMapper;
  }


  @GetMapping()
  public List<PictureDto> donwloadFiles() {
    List<Picture> pictures = pictureService.getPictures();
    List<PictureDto> pictureDtos = new ArrayList<>();
    for (Picture picture : pictures) {
      pictureDtos.add(modelMapper.map(picture, PictureDto.class));
    }
    return pictureDtos;
  }

  @GetMapping("/{id}")
  public PictureDto donwloadFile(@PathVariable long id) {
    Optional<Picture> picture = pictureService.getPicture(id);
    if (picture.isPresent()) return modelMapper.map(picture, PictureDto.class);
    else throw new HttpNotFoundError();
  }

  @GetMapping("/{id}/show")
  public ResponseEntity<byte[]> showPicture(@PathVariable long id) {
    Optional<Picture> picture = pictureService.getPicture(id);
    if (picture.isPresent())
      return ResponseEntity.
        ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(picture.get().getContent());
    else throw new HttpNotFoundError();
  }

}










