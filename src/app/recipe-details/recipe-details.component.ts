import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {DetailedRecipe} from "../model/detailed-recipe.model";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {Review} from "../model/review.model";
import {AuthService} from "../service/auth.service";
import {filter} from "rxjs/operators";
import {User} from "../model/user.model";

@Component({
  selector: 'recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})

export class RecipeDetailsComponent implements OnInit {
  routeId: bigint;
  recipe: DetailedRecipe;

  private routeSub: Subscription;

  constructor(
    private authService: AuthService,
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
    let url = 'http://localhost:8080/recipe/' + this.routeId;
    this.http.get<Observable<DetailedRecipe>>(url).subscribe((response) => {
      this.recipe = new DetailedRecipe().map(response); // enable when back-end returns ingredients and pictures
      //this.recipe = this.createStubRecipe();

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

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.routeId = params['id'];

    });

    this.showRecipe();
  }

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  get isRecipeCreator(): boolean {
    let loggedInUser = this.authService.username;
    if (loggedInUser != null && this.recipe.user != null && loggedInUser == this.recipe.user.username){
      return true;
    }
    return false;
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

  rememberRouteAndGotoLogin(){
    this.router.navigate(['/login', {previous: '/recipe/' + this.routeId}])
  }

  createStubRecipe(): DetailedRecipe {
    let stub: DetailedRecipe = new DetailedRecipe();
    stub.name = "Vlugge Noedelsoep met Paddenstoelen";
    stub.description = "Vlugge wortel-Gemberpickle, lente-uitjes & sesamstrooisel";
    stub.instructions =
      "Pel en schil de knoflook en gember, snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur. Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken. Boek intussen de wortel, rasp hem met de rode peper op een grove rasp en meng er de sushigember door. Maak de lente-uitjes schoon, snijd ze in dunne ringen en zet ze opzij." +
      "\n\nRoer wanneer de tijd erop zit de misopasta en 2 eetlepels sojasaus door de bouillon. Kook de eiernoedels volgens de aanwijzingen op de verpakking en verdeel ze over view warme kommen. Breng de bouillon op smaak met sojasaus en zwarte peper. Snijd de stronkjes paksoi door midden of in kwarten, doe ze met de paddenstoelen (die hebben verschillende vormen en maten, dus bepaal zelf welke je snijdt, scheurt of heel laat) bij de bouillon, en kook ze niet langer dan 1 minuut, om de smaak lekker vers te houden. Verdeel groenten over de kommen, schep er de dampende bouillon over en serveer de soep met de pickles, lente-uitjes en een schepje sesamzaad." +
      "\n\nEen kneepje limoensap is ook erg lekker.";
    stub.servings = 4;
    stub.user = new User();
    stub.ingredients = [
      // {measurement: "400g", name: "gemengde paddestoelen"},
      // {measurement: "1", name: "rode ui"},
      // {measurement: "2", name: "tenen knoflook"},
      // {measurement: "4", name: "zilveruitjes"},
      // {measurement: "2", name: "cornichons"},
      // {measurement: "4", name: "takjes verse badpeterselie"},
      // {measurement: "", name: "olijfolie"},
      // {measurement: "1 el", name: "kappertjes"},
      // {measurement: "50 ml", name: "whisky"},
      // {measurement: "", name: "gerookte-parikapoeder"},
      // {measurement: "80g", name: "demi creme fraiche"}
    ];
    stub.pictures = [["assets/image/background2.jpg", "Voorbereiden van de ingredienten"],
      ["assets/image/background.jpg", "Kook de eiernoedels"],
      ["assets/image/beef_stroganoff.jpg", "De maaltijd is klaar"]];
    stub.steps = [
      {
        number: 1,
        details: "Snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur."
      },
      {
        number: 2,
        details: "Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken."
      }
    ]
    stub.reviews = [
      new Review(1, 5, "matig", "vond het helemaal niks, maar ik geef hem toch een 5.", []),
      new Review(2, 4 ,"heerlijk.","kon er van genieten.", [])
    ]
    return stub;
  }
}
