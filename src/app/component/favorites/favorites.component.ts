import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  private readonly baseUrl = environment.apiUrl
  userId: bigint;
  favoriteId: bigint;
  favorites: any;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
  ) {

  }
  openRecipe(id: number) {
    this.router.navigate(['recipe/' + id])
  }


  showFavorite() {
    let url = this.baseUrl+'/user/' + this.userId + '/favorites/' + this.favoriteId;
    console.log(url)
    this.http.get(url).subscribe((response) => this.favorites = response);
  }

    ngOnInit() {
      this.routeSub = this.route.params.subscribe(params => {
        this.userId = params['id1'];
        this.favoriteId = params['id2'];
        console.log(this.userId);
        console.log(this.favoriteId);
        this.showFavorite();
      });
    }
  }
