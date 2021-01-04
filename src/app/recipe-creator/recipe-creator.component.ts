import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpHeaders} from "@angular/common/http";
import {Recipe} from "../model/recipe";
import {RecipeService} from "../service/recipe.service";
import {Ingredient} from "../model/ingredient";
import {IngredientService} from "../service/ingredient.service";
import {RecipeIngredientService} from "../service/recipe-ingredient.service";

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.css']
})
export class RecipeCreatorComponent implements OnInit {
  recipe: Recipe = {
    id: null,
    name: null,
    description: null,
    instructions: null,
    servings: null,
    ingredients: [],
    pictures: [],
    steps: [
      {number: 1, details: ''}
    ],
    reviews: [],
  };
  ingredients: Ingredient[] = []
  ingredientOptions: any[] = []
  loading: any;

  constructor(
    private router: Router,
    private recipeService: RecipeService,
    private recipeIngredientService: RecipeIngredientService,
    private ingredientService: IngredientService,
  ) {
  }

  addStep() {
    this.recipe.steps.push({
      number: this.recipe.steps.length + 1,
      details: '',
    });
  }


  ngOnInit() {
  }

  selectedFile1: File = null;
  picture1: string
  selectedFile2: File = null;
  picture2: string

  handleUpload1(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    this.selectedFile1 = <File>event.target.files[0];
    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.picture1 = reader.result.toString();
    };
  }

  handleUpload2(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    this.selectedFile2 = <File>event.target.files[0];
    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.picture2 = reader.result.toString();
    };
  }


  findIngredient(i) {
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    this.ingredientOptions = [];

    this.ingredientService.search(this.ingredients[i].name, headers).subscribe(options => {
      this.ingredientOptions = options
    });
  }


  addIngredient() {
    this.recipe.ingredients.push({
      id: {recipeId: null, ingredientId: null,},
      measurement: null,
      ingredientName: null,
    });
    this.ingredients.push({
      id: null,
      name: null,
    })
  }

  removeIngredient(i: number) {
    this.recipe.ingredients.splice(i, 1);
    this.ingredients.splice(i, 1);
  }


  submitButtonPressed() {
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    this.ingredientService.create(this.ingredients, headers).subscribe(ingredients => {
      this.ingredients = ingredients
      for (let i = 0; i < ingredients.length; i++) {
        this.recipe.ingredients[i].id.ingredientId = ingredients[i].id
      }
      this.createRecipe()
    })
  }


  createRecipe() {
    let pictures = [
      {
        name: this.selectedFile1.name,
        type: this.selectedFile1.type,
        content: this.picture1.split(',')[1]
      },
      {
        name: this.selectedFile2.name,
        type: this.selectedFile2.type,
        content: this.picture2.split(',')[1]
      }
    ]
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    this.recipe.pictures = pictures;

    this.recipeService.create(this.recipe, headers).subscribe(recipe => {
      if (recipe) {
        console.log(this.recipe);
        for (let i = 0; i < this.recipe.ingredients.length; i++) {
          this.recipe.ingredients[i].id.recipeId = recipe.id
        }
        this.recipeIngredientService.create(this.recipe.ingredients, headers).subscribe(isValid => {
          if (isValid) {
            this.router.navigate(['']);
          } else {
            alert("Ingredients failed")
          }
        })
      } else {
        alert("Recipe creation failed.")
      }
    });
  }
}
