import {Component, OnInit} from '@angular/core';
import {ListedRecipe} from '../model/listed-recipe';
import {ListedRecipeService} from '../service/listed-recipe.service';
import {Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  recipes: ListedRecipe[];

  constructor(
    private recipeService: ListedRecipeService,
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
}
