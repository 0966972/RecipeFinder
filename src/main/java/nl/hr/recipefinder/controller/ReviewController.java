package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.ReviewDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Review;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpBadRequestError;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping()
public class ReviewController {

  private final ReviewService reviewService;
  private final ModelMapper modelMapper;

  @Autowired
  public ReviewController(ReviewService reviewService, ModelMapper modelMapper) {
    this.reviewService = reviewService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/recipe/{recipeId}/review")
  public ResponseEntity<List<Review>> getReviewsForOneRecipe(@PathVariable("recipeId") Long recipeId){
    List<Review> reviews = reviewService.findAllByRecipeId(recipeId);
    return new ResponseEntity<>(reviews, HttpStatus.OK);
  }

  @PostMapping("/recipe/{recipeId}/review")
  public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto, @PathVariable("recipeId") Long recipeId){

    try{
      Review mappedReview = modelMapper.map(reviewDto, Review.class);
      Review savedReview = reviewService.save(mappedReview, recipeId);

      return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
    catch (ConstraintViolationException e){
      throw new HttpBadRequestError(e);
    }
  }

  @GetMapping("/review")
  public ResponseEntity<List<Review>> getReviewsForAllRecipes(){
    List<Review> reviews = reviewService.findAll();
    return new ResponseEntity<>(reviews, HttpStatus.OK);
  }
}
