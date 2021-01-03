import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {RecipeIngredient} from "../model/recipe-ingredient";

@Injectable({
  providedIn: 'root'
})
export class RecipeIngredientService {

  private readonly recipesUrl: string;

  constructor(private http: HttpClient) {
    this.recipesUrl = 'http://localhost:8080/recipeIngredient';
  }

  public create(recipeIngredients: RecipeIngredient[], headers): Observable<boolean> {
    return this.http.post<boolean>(this.recipesUrl, recipeIngredients, {headers: headers});
  }
}
