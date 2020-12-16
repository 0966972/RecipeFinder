package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.RecipeFinderApplication;
import nl.hr.recipefinder.model.dto.ReviewDto;
import nl.hr.recipefinder.model.dto.UserRequestDto;
import nl.hr.recipefinder.model.entity.Picture;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Review;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpBadRequestError;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class})
class ReviewControllerTests {

  @Autowired
  @InjectMocks
  ReviewController reviewController;

  @MockBean
  ReviewService reviewService;

  @MockBean
  ModelMapper modelMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void createReview_whenReviewIsValid_thenReturnsSavedReview() {
    // arrange
    Review review = new Review();
    ReviewDto reviewDto = new ReviewDto();
    Recipe recipe = new Recipe();
    long id = 1;
    recipe.setId(id);

    Mockito.when(modelMapper.map(reviewDto, Review.class)).thenReturn(review);
    Mockito.when(reviewService.save(review, id)).thenReturn(review);

    // act
    ResponseEntity<Review> result = reviewController.createReview(reviewDto, id);
    Review resultBody = result.getBody();

    // assert
    assertThat(resultBody).isInstanceOf(Review.class);

    verify(reviewService, times(1)).save(review, id);
    verify(modelMapper, times(1)).map(reviewDto, Review.class);
  }

  @Test
  void createReview_whenReviewScoreInvalid_Return400BadRequest() {
    // arrange
    long recipeId = 1;
    Recipe recipe = new Recipe();
    recipe.setId(recipeId);
    Integer invalidScore = 6;
    Review review = new Review(invalidScore, "dit is een review", new ArrayList<>(), recipe);
    ReviewDto reviewDto = new ReviewDto(invalidScore, "dit is een review", new ArrayList<>());



    Mockito.when(modelMapper.map(reviewDto, Review.class)).thenReturn(review);
    Mockito.when(reviewService.save(review, recipeId)).thenThrow(HttpBadRequestError.class);

    // act
    try{
      ResponseEntity<Review> result = reviewController.createReview(reviewDto, recipeId);
    }
    catch (HttpBadRequestError e){
      assertThat(e).isInstanceOf(HttpBadRequestError.class);
    }

    // assert
    verify(reviewService, times(1)).save(review, recipeId);
    verify(modelMapper, times(1)).map(reviewDto, Review.class);
  }

  @Test
  void createReview_whenReviewMessageInvalid_Return400BadRequest() {
    // arrange
    Recipe recipe = new Recipe();
    long recipeId = 1;
    recipe.setId(recipeId);

    String invalidMessage = "";
    Review review = new Review(5, invalidMessage, new ArrayList<>(), recipe);
    ReviewDto reviewDto = new ReviewDto(5, invalidMessage, new ArrayList<>());


    Mockito.when(modelMapper.map(reviewDto, Review.class)).thenReturn(review);
    Mockito.when(reviewService.save(review, recipeId)).thenThrow(HttpBadRequestError.class);

    // act
    try{
      ResponseEntity<Review> result = reviewController.createReview(reviewDto, recipeId);
    }
    catch (HttpBadRequestError e){
      assertThat(e).isInstanceOf(HttpBadRequestError.class);
    }

    // assert
    verify(reviewService, times(1)).save(review, recipeId);
    verify(modelMapper, times(1)).map(reviewDto, Review.class);
  }

  @Test
  void getReviewsForOneRecipe_whenReviewsFound_ReturnReviewsList() {
    // arrange
    Recipe recipe = new Recipe();
    long recipeId = 1;
    recipe.setId(recipeId);

    Review review = new Review(5, "een review", new ArrayList<>(), recipe);
    List<Review> reviews = new ArrayList<>();
    reviews.add(review);
    Mockito.when(reviewService.findAllByRecipeId(recipeId)).thenReturn(reviews);

    // act
    ResponseEntity<List<Review>> result = reviewController.getReviewsForOneRecipe(recipeId);
    List<Review> resultBody = result.getBody();

    assertThat(resultBody).isEqualTo(reviews);

    // assert
    verify(reviewService, times(1)).findAllByRecipeId(recipeId);
  }

  @Test
  void getReviewsForOneRecipe_whenReviewsNotFound_ReturnsEmptyList() {
    // arrange
    Recipe recipe = new Recipe();
    long recipeId = 1;
    recipe.setId(recipeId);

    List<Review> emptyList = new ArrayList<>();
    Mockito.when(reviewService.findAllByRecipeId(recipeId)).thenReturn(emptyList);

    // act
    ResponseEntity<List<Review>> result = reviewController.getReviewsForOneRecipe(recipeId);
    List<Review> resultBody = result.getBody();

    assertThat(resultBody).isEqualTo(emptyList);

    // assert
    verify(reviewService, times(1)).findAllByRecipeId(recipeId);
  }

  @Test
  void getReviewsForAllRecipes_whenReviewsFound_ReturnReviewsList() {
    // arrange
    Recipe recipe = new Recipe();
    long recipeId = 1;
    recipe.setId(recipeId);

    Review review = new Review(5, "een review", new ArrayList<>(), recipe);
    List<Review> reviews = new ArrayList<>();
    reviews.add(review);
    Mockito.when(reviewService.findAll()).thenReturn(reviews);

    // act
    ResponseEntity<List<Review>> result = reviewController.getReviewsForAllRecipes();
    List<Review> resultBody = result.getBody();

    assertThat(resultBody).isEqualTo(reviews);

    // assert
    verify(reviewService, times(1)).findAll();
  }

  @Test
  void getReviewsForAllRecipes_whenReviewsNotFound_ReturnEmptyList() {
    // arrange
    Recipe recipe = new Recipe();
    long recipeId = 1;
    recipe.setId(recipeId);
    List<Review> reviews = new ArrayList<>();

    Mockito.when(reviewService.findAll()).thenReturn(reviews);

    // act
    ResponseEntity<List<Review>> result = reviewController.getReviewsForAllRecipes();
    List<Review> resultBody = result.getBody();

    assertThat(resultBody).isEqualTo(reviews);

    // assert
    verify(reviewService, times(1)).findAll();
  }
}
