import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ListedRecipe} from '../model/listed-recipe.model';
import {Observable} from 'rxjs/Observable';
import {Recipe} from "../model/recipe.model";
import {Ingredient} from "../model/ingredient.model";
import {DetailedRecipe} from "../model/detailed-recipe.model";

@Injectable()
export class RecipeService {

    private readonly recipesUrl: string;

    constructor(private http: HttpClient) {
        this.recipesUrl = 'http://localhost:8080/recipe';
    }

    public findAll(): Observable<ListedRecipe[]> {
        return this.http.get<ListedRecipe[]>(this.recipesUrl);
    }

    public find(id: number): Observable<DetailedRecipe> {
        return this.http.get<DetailedRecipe>(this.recipesUrl + "/" + id);
    }

    public search(searchInput: String, filterIngredients: Ingredient[], score: number): Observable<ListedRecipe[]> {
        let post = {
            searchInput: searchInput,
            ingredients: filterIngredients.map(it => it.name),
            minimumScore: score,
        };
        let url = 'http://localhost:8080/recipe/search';
        return this.http.post<ListedRecipe[]>(url, post);
    }

    public create(recipe: Recipe, headers): Observable<Recipe> {
        return this.http.post<Recipe>(this.recipesUrl, recipe, {headers: headers});
    }

}
