import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AdminIngredient} from "../model/admin-ingredient.model";
import {User} from "../model/user.model";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly ingredientsUrl: string;
  private readonly usersUrl: string;
  private readonly headers: any;

  constructor(private http: HttpClient) {
    this.ingredientsUrl = 'http://localhost:8080/admin/ingredients';
    this.usersUrl = 'http://localhost:8080/user';

    this.headers = new HttpHeaders({
      authorization: 'Basic ' + sessionStorage.getItem('token')
    });
  }

  public banUser(user: User): Observable<User> {
    return this.http.patch<User>(this.usersUrl + '/ban/' + user.id, {},{headers: this.headers});
  }

  public acceptIngredient(ingredient: AdminIngredient): Observable<AdminIngredient> {
    ingredient.acceptedState = "ACCEPTED"
    return this.http.patch<AdminIngredient>(this.ingredientsUrl, ingredient, {headers: this.headers});
  }

  public rejectIngredient(ingredient: AdminIngredient): Observable<AdminIngredient> {
    ingredient.acceptedState = "REFUSED"
    return this.http.patch<AdminIngredient>(this.ingredientsUrl, ingredient, {headers: this.headers});
  }

  public getPendingIngredients(): Observable<AdminIngredient[]> {
    return this.http.get<AdminIngredient[]>(this.ingredientsUrl + '/pending', {headers: this.headers});
  }

  public getRefusedIngredients(): Observable<AdminIngredient[]> {
    return this.http.get<AdminIngredient[]>(this.ingredientsUrl + '/refused', {headers: this.headers});
  }
}
