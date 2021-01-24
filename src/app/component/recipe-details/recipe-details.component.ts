import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {DetailedRecipe} from "../../model/detailed-recipe.model";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {AuthService} from "../../service/auth/auth.service";
import {filter} from "rxjs/operators";
import {FavoritesListService} from "../../service/favorites-list/favorites-list.service";
import {FavoritesList} from "../../model/favorites-list.model";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})

export class RecipeDetailsComponent implements OnInit {
  private readonly baseUrl = environment.apiUrl
  routeId: bigint;
  recipe: DetailedRecipe;
  isReportingAllowed: boolean;

  private routeSub: Subscription;

  constructor(
    private authService: AuthService,
    private favoritesListService: FavoritesListService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private cdr:ChangeDetectorRef,
    carouselConfig: NgbCarouselConfig
  ) {
    carouselConfig.interval = 0;
    carouselConfig.keyboard = true;
    carouselConfig.pauseOnHover = true;
    carouselConfig.showNavigationIndicators = true;

    this.recipe = new DetailedRecipe();
  }

  showRecipe() {
    let url = this.baseUrl+'/recipe/' + this.routeId;
    this.http.get<Observable<DetailedRecipe>>(url).subscribe((response) => {
      this.recipe = new DetailedRecipe().map(response);
      this.getIsReportingAllowed()
    }, error => {
      switch (error) {
        case 500:
          alert("500: De server is helaas niet bereikbaar, probeer het later nog eens.");
          this.router.navigate(['']);
          break;
        case 404:
          alert("404: Het opgevraagde recept konden we helaas niet vinden.");
          this.router.navigate(['']);
          break;
        default:
          alert("Er ging iets mis, probeer het later nog eens.");
          this.router.navigate(['']);
      }
    });


  }

  getIsReportingAllowed() {
    let url = this.baseUrl + '/report/allowed/' + this.recipe.user.id;
    let headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + sessionStorage.getItem('token')
    });

    this.http.get<any>(url, {headers: headers}).subscribe((response) => {
      this.isReportingAllowed = response
    }, error => {
    });
  }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.routeId = params['id'];

    });

    this.showRecipe();
    if (this.isLoggedIn){
      this.favoritesListService.getAll().subscribe(list => {
        this.favoritesList = list
      });
    }
  }

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  get isRecipeCreator(): boolean {
    let loggedInUser = this.authService.username;
    return loggedInUser != null && this.recipe.user != null && loggedInUser == this.recipe.user.username;
  }

  gotoReview(){
    this.router.navigate(['recipe/' + this.routeId + '/review-create', {previous: '/recipe/' + this.routeId}]).then(
      ()=>
      this.router.events
        .pipe(
          filter(value => value instanceof NavigationEnd),
        )
        .subscribe(() => {
          this.showRecipe();
        })
    );
  }

  gotoReport(){
    this.router.navigate(['report-user/', {previous: '/recipe/' + this.routeId}]).then(
      ()=>
        this.router.events
          .pipe(
            filter(value => value instanceof NavigationEnd),
          )
          .subscribe(() => {
            this.showRecipe();
          })
    );
  }

  get showingFavoritesList() : boolean {
    return this.showFavoritesList;
  }
  showFavoritesList = false;
  favoritesList: FavoritesList[];

  toggleFavoriteLists(){
    if(!this.showFavoritesList){
      this.favoritesListService.getAll().subscribe(list => {
          this.favoritesList = list
      });
    }
    this.showFavoritesList = !this.showFavoritesList;
  }

  rememberRouteAndGotoLogin(){
    this.router.navigate(['/login', {previous: '/recipe/' + this.routeId}])
  }

  addToFavoritesList(listId){
    let token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    this.favoritesListService.addToList(this.recipe.id, listId, headers).subscribe(val => this.setCheck(listId))
  }

  setCheck(id){
    let button = document.getElementById("list"+id);
    button.classList.remove("btn-success");
    button.classList.add("btn-primary");

    let icon = document.getElementById("list"+id+"icon");
    icon.classList.remove("fa-plus");
    icon.classList.add("fa-check");
  }
}
