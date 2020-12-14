import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {DetailedRecipe} from "../model/detailed-recipe.model";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})

export class RecipeDetailsComponent implements OnInit {

  loaded = false;
  routeId: bigint;
  recipe: DetailedRecipe;

  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    carouselConfig: NgbCarouselConfig
  ) {
    carouselConfig.interval = 4000;
    carouselConfig.keyboard = true;
    carouselConfig.pauseOnHover = true;
    carouselConfig.showNavigationIndicators = true;

    this.recipe = new DetailedRecipe();
  }

  showRecipe() {
    let url = 'http://localhost:8080/recipe/' + this.routeId;
    let token: string = '' + sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });

    this.http.get<Observable<DetailedRecipe>>(url).subscribe((response) => {


      this.recipe = new DetailedRecipe().map(response); // enable when back-end returns ingredients and pictures

      // this.recipe = this.createStubRecipe();

    }, error => {
      switch (error) {
        case 500:
          alert("De server is helaas niet bereikbaar, probeer het later nog eens.");
          this.router.navigate(['']);
          break;
        case 404:
          alert("Het opgevraagde recept konden we helaas niet vinden.");
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

  createStubRecipe(): DetailedRecipe {
    let stub: DetailedRecipe = new DetailedRecipe();
    stub.name = "Vlugge Noedelsoep met Paddenstoelen";
    stub.description = "Vlugge wortel-Gemberpickle, lente-uitjes & sesamstrooisel";
    stub.instructions =
      "Pel en schil de knoflook en gember, snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur. Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken. Boek intussen de wortel, rasp hem met de rode peper op een grove rasp en meng er de sushigember door. Maak de lente-uitjes schoon, snijd ze in dunne ringen en zet ze opzij." +
      "\n\nRoer wanneer de tijd erop zit de misopasta en 2 eetlepels sojasaus door de bouillon. Kook de eiernoedels volgens de aanwijzingen op de verpakking en verdeel ze over view warme kommen. Breng de bouillon op smaak met sojasaus en zwarte peper. Snijd de stronkjes paksoi door midden of in kwarten, doe ze met de paddenstoelen (die hebben verschillende vormen en maten, dus bepaal zelf welke je snijdt, scheurt of heel laat) bij de bouillon, en kook ze niet langer dan 1 minuut, om de smaak lekker vers te houden. Verdeel groenten over de kommen, schep er de dampende bouillon over en serveer de soep met de pickles, lente-uitjes en een schepje sesamzaad." +
      "\n\nEen kneepje limoensap is ook erg lekker.";
    stub.servings = 4;
    stub.creator = "Bentley";
    stub.ingredients = [["400g", "gemengde paddestoelen"], [ "1", "rode ui"], ["2", "tenen knoflook"], ["4", "zilveruitjes"], ["2", "cornichons"],
      ["4", "takjes verse badpeterselie"], ["", "olijfolie"], ["1 el", "kappertjes"], ["50 ml", "whisky"], ["", "gerookte-parikapoeder"], ["80g", "demi creme fraiche"]];
    stub.pictures = [["assets/image/background2.jpg", "Voorbereiden van de ingredienten"],
      ["assets/image/background.jpg", "Kook de eiernoedels"],
      ["assets/image/beef_stroganoff.jpg", "De maaltijd is klaar"]];
    stub.steps = ["Pel en schil de knoflook en gember.", "Snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur.",
      "Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken." ]
    return stub;
  }
}
