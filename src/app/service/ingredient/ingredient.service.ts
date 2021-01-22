import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Ingredient} from "../../model/ingredient.model";

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  private readonly ingredientUrl: string;

  constructor(private http: HttpClient) {
    this.ingredientUrl = 'http://localhost:8080/ingredient';
  }

  public create(ingredients, headers): Observable<Ingredient[]> {
    return this.http.post<Ingredient[]>(this.ingredientUrl, ingredients, {headers: headers});
  }

  public search(searchInput): Observable<any[]> {
    let url = this.ingredientUrl + '/search/' + searchInput;
    return this.http.get<any[]>(url);
  }
}
