import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AdminIngredient} from "../model/admin-ingredient";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly ingredientsUrl: string;
  private readonly headers: any;

  constructor(private http: HttpClient) {
    this.ingredientsUrl = 'http://localhost:8080/admin/ingredients';

    this.headers = new HttpHeaders({
      authorization: 'Basic ' + sessionStorage.getItem('token')
    });
  }

  public acceptIngredient(ingredient: AdminIngredient): Observable<AdminIngredient> {
    ingredient.acceptedState = "ACCEPTED"
    return this.http.post<AdminIngredient>(this.ingredientsUrl, ingredient, {headers: this.headers});
  }

  public rejectIngredient(ingredient: AdminIngredient): Observable<AdminIngredient> {
    ingredient.acceptedState = "REFUSED"
    return this.http.post<AdminIngredient>(this.ingredientsUrl, ingredient, {headers: this.headers});
  }

  public getPendingIngredients(): Observable<AdminIngredient[]> {
    return this.http.get<AdminIngredient[]>(this.ingredientsUrl + '/pending', {headers: this.headers});
  }

  public getRefusedIngredients(): Observable<AdminIngredient[]> {
    return this.http.get<AdminIngredient[]>(this.ingredientsUrl + '/refused', {headers: this.headers});
  }
}
