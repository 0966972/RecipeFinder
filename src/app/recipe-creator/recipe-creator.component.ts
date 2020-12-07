import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

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
    // let pictures = [{
    //   name : this.model.pictures,
    //   content : this.model.pictures.binaryType
    // }]
    let postData = {
      name: this.model.name,
      description: this.model.description,
      instructions: this.model.instructions
      // pictures
    };


    this.http.post<Observable<boolean>>(url, postData).subscribe(isValid => {
      if (isValid) {
        console.log(postData);
        this.router.navigate(['']);
      } else {
        alert("User creation failed.")
      }

    });

  }

}
