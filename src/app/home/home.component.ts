import {Component, OnInit} from '@angular/core';
import {ListedRecipe} from '../model/listed-recipe';
import {RecipeService} from '../service/recipe.service';
import {Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  recipes: ListedRecipe[];
  searchInput: ''
  picture: ''

  constructor(
    private recipeService: RecipeService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.recipeService.findAll().subscribe(data => {
      this.recipes = data
    })
    ;
  }

  getPicture(){

  }



  openRecipe(id: number) {
    this.router.navigate(['recipe/' + id])
  }

  searchInputChanged() {
    this.recipeService.search(this.searchInput).subscribe(data => {
      this.recipes = data;
    });
  }
}
