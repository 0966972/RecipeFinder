import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";


@Component({
  selector: 'recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})

export class RecipeDetailsComponent implements OnInit {

  model: any = {};
  loading: any;
  currentRoute: string;

  id: bigint;
  title: string;
  description: string;
  servings: number;
  instructions: string;
  creatorName: string;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {

  }

  showRecipe() {
    let url = 'http://localhost:8080/recipe/' + this.id;
    let token: string = '' + sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });

    this.http.get<Observable<Object>>(url, {headers: headers}).subscribe((response) => {
      console.log(response)
      this.title = response['name'];
      this.description = response['description'];
      this.instructions = response['instructions'];
      this.servings = response['servings'];
    }, error => {
      if (error) {
        alert("Er ging iets mis, probeer het later nog eens.")
      }
    });
  }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.showRecipe()
  }
}
