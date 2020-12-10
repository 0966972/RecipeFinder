package nl.hr.recipefinder.service;


import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

  private final PictureRepository pictureRepository;

  public PictureService(PictureRepository pictureRepository) {
    this.pictureRepository = pictureRepository;
  }

  public Picture storePicture(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Picture Picture = new Picture(fileName, file.getContentType(), file.getBytes());

    return pictureRepository.save(Picture);
  }

  public List<Picture> getPictures() {
    return pictureRepository.findAll();
  }

  public Picture getPicture(long id){
    return pictureRepository.findById(id).get();
  }
}




