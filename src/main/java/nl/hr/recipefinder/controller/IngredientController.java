package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
  private final IngredientService ingredientService;
  private final ModelMapper modelMapper;

  @Autowired
  public IngredientController(IngredientService ingredientService, ModelMapper modelMapper) {
    this.ingredientService = ingredientService;
    this.modelMapper = modelMapper;
  }
}



