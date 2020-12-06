import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  // styleUrls: ['./recipe-creator.component.css']
})
export class RecipeCreatorComponent implements OnInit {
  model: any = {};
  loading: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
  }


  ngOnInit(): void {
  }


  CreateRecipe() {
    let url = 'http://localhost:8080/recipe';
    let postData = {
      name: this.model.name,
      description: this.model.description,
      instructions: this.model.instructions
    };
    this.http.post(url, postData).toPromise().then(data => {
      console.log(data)
    });
  }

}
