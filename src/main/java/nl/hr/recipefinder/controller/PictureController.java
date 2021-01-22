package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.PictureDto;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/picture")
public class PictureController {
  private final PictureService pictureService;
  private final ModelMapper modelMapper;

  @GetMapping()
  public List<PictureDto> downloadFiles() {
    List<Picture> pictures = pictureService.getPictures();

    return pictures.stream()
      .map(picture -> modelMapper.map(picture, PictureDto.class))
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public PictureDto downloadFile(@PathVariable long id) {
    Optional<Picture> picture = pictureService.getPicture(id);
    return picture.map(p -> modelMapper.map(picture, PictureDto.class))
      .orElseThrow(HttpNotFoundException::new);
  }

  @GetMapping("/{id}/show")
  public ResponseEntity<byte[]> showPicture(@PathVariable long id) {
    Optional<Picture> picture = pictureService.getPicture(id);
    if (picture.isPresent())
      return ResponseEntity.
        ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(picture.get().getContent());
    else throw new HttpNotFoundException();
  }

}










