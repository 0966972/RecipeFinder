package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.repository.PictureRepository;
import nl.hr.recipefinder.service.PictureService;
import nl.hr.recipefinder.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;

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

  @PostMapping()
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      pictureService.storePicture(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(message);
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }
  }

  @GetMapping()
  public List<Picture> donwloadFiles() {
    return pictureService.getPictures();
  }
  @GetMapping("/{id}")
  public Picture donwloadFile(@PathVariable long id) {
    return pictureService.getPicture(id);
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










