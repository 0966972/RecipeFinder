import {Component, OnInit} from '@angular/core';
import {ListedRecipe} from '../model/listed-recipe.model';
import {RecipeService} from '../service/recipe.service';
import {Router} from "@angular/router";
import {Ingredient} from "../model/ingredient.model";
import {IngredientService} from "../service/ingredient.service";

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

    recipes: ListedRecipe[];
    picture: ''
    searchInput: '' = ''
    doFilterScore = false
    scoreFilterNum = 1
    scoreFilter = []
    filterIngredients: Ingredient[] = []
    ingredientOptions: any[] = []

    constructor(
        private recipeService: RecipeService,
        private ingredientService: IngredientService,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.recipeService.findAll().subscribe(data => {
            this.recipes = data
        });
    }

    openRecipe(id: number) {
        this.router.navigate(['recipe/' + id])
    }


    findIngredient(i) {
        this.ingredientOptions = [];

        this.ingredientService.search(this.filterIngredients[i].name).subscribe(options => {
            this.ingredientOptions = options
        });

        this.searchInputChanged()
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


    showScoreFilter(show: boolean) {
        this.doFilterScore = show
        if (show) {
            this.scoreFilter = [true, false, false, false, false]
            this.scoreFilterNum = 1
        } else {
            this.scoreFilter = []
            this.scoreFilterNum = null
        }
        this.searchInputChanged()
    }

    scoreSelected(i) {
        this.scoreFilterNum = i;
        this.scoreFilter = []
        for (let j = 0; j < 5; j++)
            this.scoreFilter.push(j <= i);
        this.searchInputChanged()
    }


    searchInputChanged() {
        this.recipeService.search(this.searchInput, this.filterIngredients, this.scoreFilterNum).subscribe(data => {
            this.recipes = data;
        });
    }
}
