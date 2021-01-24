import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {RecipeIngredient} from "../../model/recipe-ingredient.model";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RecipeIngredientService {
  private readonly baseUrl = environment.apiUrl
  private readonly recipesUrl = this.baseUrl + '/recipeingredient'

  constructor(private http: HttpClient) { }

  public create(recipeIngredients: RecipeIngredient[], headers): Observable<boolean> {
    return this.http.post<boolean>(this.recipesUrl, recipeIngredients, {headers: headers});
  }
}
