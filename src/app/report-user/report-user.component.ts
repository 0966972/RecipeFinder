import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../service/auth.service";
import {RecipeService} from "../service/recipe.service";
import {ReportService} from "../service/report.service";
import {DetailedRecipe} from "../model/detailed-recipe.model";
import {ReportRequest} from "../model/report-request.model";

@Component({
  selector: 'report-user',
  templateUrl: './report-user.component.html',
  styleUrls: ['./report-user.component.css']
})

export class ReportUserComponent implements OnInit {
  routeId: number;
  report;
  recipe: DetailedRecipe;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService,
    private recipeService: RecipeService,
    private reportService: ReportService
  ) {
    this.report = new ReportRequest()
  }

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  ngOnInit() {
    this.route.parent.url.subscribe(() => {
      let path = this.route.snapshot.paramMap.get('previous');
      this.routeId = parseInt(path.replace("/recipe/", ""));
      this.report.reportingUserId = this.authService.getUserId()
      this.getRecipe();
    });
  }

  submitReport() {
    if (this.validateInput()) {
      this.reportUser();
    }
  }

  validateInput(): boolean {
    if (this.validateString(this.report.message)) {
      alert("Vul een bericht in.")
      return false;
    }
    return true;
  }

  validateString(string: string) {
    return !string || !string.trim() || string == '';
  }

  reportUser() {
    let body = this.report;

    const headers = new HttpHeaders()
      .set('Authorization', 'Basic ' + sessionStorage.getItem('token'))
      .set('Content-Type', 'application/json');
    this.reportService.reportUser(body, headers).subscribe(() => {
      this.navigate();
    }, error => {
      this.displayHttpError(error.status);
    });
  }

  getRecipe() {
    this.recipeService.find(this.routeId).subscribe((recipeResponse) => {
      this.recipe = new DetailedRecipe().map(recipeResponse);
      this.report.reportedRecipeId = this.recipe.id;
      this.report.reportedUserId = this.recipe.id;
    }, error => {
      this.displayHttpError(error.status);
    });
  }

  navigate() {
    let previousRoute = this.route.snapshot.paramMap.get('previous');
    if (previousRoute) {
      this.router.navigate([previousRoute]);
    } else {
      this.router.navigate(['']);
    }
  }

  displayHttpError(status: any) {
    switch (status) {
      case 400:
        alert("400: Vul een bericht in.")
        break;
      default:
        alert("Er ging iets mis, probeer het later nog eens.")
        break;
    }
  }
}
