import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FavoritesList} from "../../model/favorites-list.model";
import {Recipe} from "../../model/recipe.model";
import { AuthService } from '../auth/auth.service';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FavoritesListService {

  private authService: AuthService;
  private readonly baseUrl = environment.apiUrl
  private readonly favoriteListsUrl = this.baseUrl+'/user'

  constructor(private http: HttpClient, private _authService: AuthService) {
    this.authService = _authService;
  }

  public getAll(): Observable<FavoritesList[]> {
    let userId = this.authService.getUserId();
    return this.http.get<FavoritesList[]>(this.favoriteListsUrl+"/"+userId+"/favorites");
  }

  public addToList(recipeId: bigint, listId: bigint, headers) : Observable<FavoritesList> {
    let userId = this.authService.getUserId();
    let url = this.favoriteListsUrl + "/"+userId+"/favorites/" + listId;
    return this.http.patch<FavoritesList>(url, recipeId, {headers: headers});
  }
}
