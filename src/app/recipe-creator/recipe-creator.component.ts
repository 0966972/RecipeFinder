import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpHeaders} from "@angular/common/http";
import {Recipe} from "../model/recipe";
import {RecipeService} from "../service/recipe.service";

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.css']
})
export class RecipeCreatorComponent implements OnInit {
  recipe: Recipe = {
    name: null,
    description: null,
    instructions: null,
    servings: null,
    ingredients: [],
    pictures: [],
    dummy: [],
    steps: [
      {number: 1, details: ''}
    ]
  };
  loading: any;

  constructor(
    private router: Router,
    private recipeService: RecipeService,
  ) {
  }

  addStep() {
    this.recipe.steps.push({
      number: this.recipe.steps.length + 1,
      details: '',
    });
  }

  addFoto() {
    if (this.recipe.dummy.length < 5){
      this.recipe.dummy.push({
        number: this.recipe.pictures.length + 1,
        details: '',
      });
    };

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
      console.log(reader.result);
      this.picture0 = reader.result.toString();
      this.recipe.pictures.push({
        number: this.recipe.pictures.length,
        name: this.selectedFile0.name,
        type: this.selectedFile0.type,
        content: this.picture0.split(',')[1]
      });
    };
    // delete this.recipe.pictures[0];
    console.log(this.recipe.pictures);
    console.log(this.recipe.pictures[0]);
    console.log(this.recipe.pictures[0].name == "dummy");


  }


  addIngredient() {
    this.recipe.ingredients.push({
      name: null,
      measurement: null,
    });
  }

  removeIngredient(i: number) {
    this.recipe.ingredients.splice(i, 1);
  }


  createRecipe() {
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });

    this.recipeService.create(this.recipe, headers).subscribe(isValid => {
      if (isValid) {
        console.log(this.recipe);
        this.router.navigate(['']);
      } else {
        alert("Recipe creation failed.")
      }
    });
  }
}
