import {Component, OnInit} from '@angular/core';
import {ListedRecipe} from '../model/listed-recipe';
import {RecipeService} from '../service/recipe.service';
import {Router} from "@angular/router";
import {Ingredient} from "../model/ingredient";

@Component({
  selector: 'home',
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  recipes: ListedRecipe[];
  searchInput: ''
  filterIngredients: Ingredient[] = []

  constructor(
    private recipeService: RecipeService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.recipeService.findAll().subscribe(data => {
      this.recipes = data;
    });
  }

  openRecipe(id: number) {
    this.router.navigate(['recipe/' + id])
  }


  addIngredient() {
    this.filterIngredients.push({
      id: null,
      name: null,
    })
  }

  removeIngredient(i: number) {
    this.filterIngredients.splice(i, 1);
    this.searchInputChanged()
  }

  searchInputChanged() {
    this.recipeService.search(this.searchInput, this.filterIngredients).subscribe(data => {
      this.recipes = data;
    });
  }
}
