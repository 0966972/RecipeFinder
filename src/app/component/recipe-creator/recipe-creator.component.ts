import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpHeaders} from "@angular/common/http";
import {Recipe} from "../../model/recipe.model";
import {RecipeService} from "../../service/recipe/recipe.service";
import {Ingredient} from "../../model/ingredient.model";
import {IngredientService} from "../../service/ingredient/ingredient.service";
import {RecipeIngredientService} from "../../service/recipe-ingredient/recipe-ingredient.service";

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.css']
})
export class RecipeCreatorComponent implements OnInit {
  showingPictures = false;
  recipe: Recipe = {
    id: null,
    name: null,
    description: null,
    preparationTime: 0,
    instructions: null,
    servings: null,
    user: null,
    ingredients: [],
    pictures: [],
    dummy: [{}],
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

  removeStep(i: number){
    for(let j = i; j < this.recipe.steps.length; j++){
      this.recipe.steps[j].number = this.recipe.steps[j].number - 1;
    }

    this.recipe.steps.splice(i, 1);
  }

  removeEmptySteps(){
    for(let i = this.recipe.steps.length - 1; i >= 0; i--){
      if(this.recipe.steps[i].details == ''){
        this.removeStep(i);
      }
    }
  }

  get showPictures() : boolean {
    if (!this.showingPictures){
      this.showingPictures = true;
      return true;
    }
    else{
      this.showingPictures = false;
      return false;
    }
  }


  ngOnInit() {
  }

  selectedFile0: File = null;
  picture0: string

  handleUpload0(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    this.selectedFile0 = <File>event.target.files[0];
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.picture0 = reader.result.toString();
      this.recipe.pictures.push({
        number: this.recipe.pictures.length,
        name: this.selectedFile0.name,
        type: this.selectedFile0.type,
        content: this.picture0.split(',')[1],
        thumbnail: false
      });
      if (this.recipe.dummy.length < 5) {
        this.recipe.dummy.push({
          number: this.recipe.pictures.length + 1,
          details: '',
        });
      };
      if (this.recipe.pictures.length == 1){
        this.recipe.pictures[0].thumbnail = true;
      };
    };

  }

  setThumbnail(i){
    console.log(i);
    for (let i = 0; i < this.recipe.pictures.length; i++) {
      this.recipe.pictures[i].thumbnail = false
    }
    this.recipe.pictures[i].thumbnail = true;
  }

  removePicture(index: number){
    if(this.recipe.pictures[index].thumbnail == true && index > 0)
    {
      this.recipe.pictures[0].thumbnail = true;
    };
    this.recipe.pictures.splice(index, 1);
    if(this.recipe.dummy.length > 1) {
      this.recipe.dummy.splice(index, 1);
    };
  }

  findIngredient(i) {
    this.ingredientOptions = [];

    this.ingredientService.search(this.ingredients[i].name).subscribe(options => {
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


  submitButtonPressed(){
    this.removeEmptySteps();
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
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
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
