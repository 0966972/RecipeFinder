package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.PictureDto;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
    Picture picture = pictureService.getPicture(id);
    PictureDto mappedpicture = modelMapper.map(picture, PictureDto.class);
    return mappedpicture;
  }

  @GetMapping("/{id}/show")
  public ResponseEntity<byte[]> showPicture(@PathVariable long id) {
    var imgFile = pictureService.getPicture(id);
    return ResponseEntity.
      ok()
      .contentType(MediaType.IMAGE_JPEG)
      .body(imgFile.getContent());
  }


}










