package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Review;
import nl.hr.recipefinder.repository.RecipeRepository;
import nl.hr.recipefinder.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final RecipeRepository recipeRepository;

  @Autowired
  public ReviewService(ReviewRepository reviewRepository, RecipeRepository recipeRepository) {
    this.reviewRepository = reviewRepository;
    this.recipeRepository = recipeRepository;
  }

  public Optional<Review> findById(Long id) {
    return reviewRepository.findById(id);
  }

  public List<Review> findAllByRecipeId(Long recipeId) {
    return reviewRepository.findAllByRecipeId(recipeId);
  }

  public List<Review> findAll() {
    return reviewRepository.findAll();
  }

  public Review save(Review review, Long recipeId) {
    Recipe parentRecipe = recipeRepository.getOne(recipeId);
    review.setRecipe(parentRecipe);
    return reviewRepository.save(review);
  }

}
