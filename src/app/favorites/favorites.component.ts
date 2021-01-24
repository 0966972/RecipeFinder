import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Clipboard} from "@angular/cdk/clipboard";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  showCopiedMessage: boolean = false;
  userId: bigint;
  favoriteId: bigint;
  favorites: any;
  private routeSub: Subscription;

  constructor(
    private clipboard: Clipboard,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
  }

  openRecipe(id: number) {
    this.router.navigate(['recipe/' + id])
  }

  get readyToShowContent() : boolean {
    return this.favorites != undefined || this.favorites != null
  }

  showFavorite() {
    let url = 'http://localhost:8080/user/' + this.userId + '/favorites/' + this.favoriteId;
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

  shareableLink() {
    this.clipboard.copy("http://localhost:4200"+this.router.url)
    this.showCopiedMessage = true
    setTimeout(() => {
      this.showCopiedMessage = false
    }, 2000)
  }

}

