import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {FavoritesList} from "../model/favorites-list.model";
import {Recipe} from "../model/recipe.model";

@Injectable({
  providedIn: 'root'
})
export class FavoritesListService {

  private readonly favoriteListsUrl: string;

  constructor(private http: HttpClient) {
    this.favoriteListsUrl = 'http://localhost:8080/user';
  }

  public getAll(): Observable<FavoritesList[]> {
    return this.http.get<FavoritesList[]>(this.favoriteListsUrl+"/2/favorites");
  }

  public addToList(recipeId: bigint, listId: bigint, headers) {
    let url = this.favoriteListsUrl + "/2/favorites/" + listId;
    return this.http.patch(url, recipeId, {headers: headers});
  }
}
