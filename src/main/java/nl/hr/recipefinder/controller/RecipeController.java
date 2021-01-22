package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.ListedRecipeDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.dto.SearchInputDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Review;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerException;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
        allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<List<ListedRecipeDto>> getRecipes() {
        List<Recipe> recipes = recipeService.getRecipes();

        return new ResponseEntity<>(recipes.stream()
                .map(it -> modelMapper.map(it, ListedRecipeDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/search")
    public ResponseEntity<List<ListedRecipeDto>> searchRecipes(@RequestBody SearchInputDto searchInput) {
        List<Recipe> recipes;
        if (searchInput.getSearchInput() != null) {
            recipes = recipeService.findRecipesByNameOrDescription(searchInput.getSearchInput());
        } else {
            recipes = recipeService.getRecipes();
        }

        List<Recipe> foundRecipes = recipeService.findMatches(searchInput, recipes);

        return new ResponseEntity<>(foundRecipes.stream()
                .map(it -> modelMapper.map(it, ListedRecipeDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("id") Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);

        if (!recipe.isPresent()) {
            throw new HttpNotFoundException();
        }

        RecipeDto recipeDto = modelMapper.map(recipe.get(), RecipeDto.class);

        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipedto) {
        try {
            User user = authenticationService.getAuthenticatedUser();
            Recipe mappedRecipe = modelMapper.map(recipedto, Recipe.class);
            mappedRecipe.setUser(user);
            mappedRecipe.setIngredients(List.of());
            Recipe savedRecipe = recipeService.save(mappedRecipe);

            return new ResponseEntity<>(modelMapper.map(savedRecipe, RecipeDto.class), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new HttpConflictException(e);
        }
    }
}
