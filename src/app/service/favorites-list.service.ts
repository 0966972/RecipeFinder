import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FavoritesList} from "../model/favorites-list.model";
import {Recipe} from "../model/recipe.model";
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class FavoritesListService {

  private authService: AuthService;
  private readonly favoriteListsUrl: string;

  constructor(private http: HttpClient, private _authService: AuthService) {
    this.favoriteListsUrl = 'http://localhost:8080/user';
    this.authService = _authService;
  }

  public getAll(): Observable<FavoritesList[]> {
    let userId = this.authService.getUserId();
    return this.http.get<FavoritesList[]>(this.favoriteListsUrl+"/"+userId+"/favorites");
  }

  public addToList(recipeId: bigint, listId: bigint, headers) : Observable<FavoritesList> {
    let userId = this.authService.getUserId();
    let url = this.favoriteListsUrl + "/"+userId+"/favorites/" + listId;
    return this.http.post<FavoritesList>(url, recipeId, {headers: headers});
  }
}
