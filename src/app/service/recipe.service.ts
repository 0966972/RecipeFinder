import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ListedRecipe} from '../model/listed-recipe';
import {Observable} from 'rxjs/Observable';
import {Recipe} from "../model/recipe";

@Injectable()
export class RecipeService {

  private readonly recipesUrl: string;

  constructor(private http: HttpClient) {
    this.recipesUrl = 'http://localhost:8080/recipe';
  }

  public findAll(): Observable<ListedRecipe[]> {
    return this.http.get<ListedRecipe[]>(this.recipesUrl);
  }

  public search(searchInput): Observable<ListedRecipe[]> {
    let url = 'http://localhost:8080/recipe/search/' + searchInput;
    return this.http.get<ListedRecipe[]>(url);
  }

  public create(recipe: Recipe, headers): Observable<Recipe> {
    return this.http.post<Recipe>(this.recipesUrl, recipe, {headers: headers});
  }
}
