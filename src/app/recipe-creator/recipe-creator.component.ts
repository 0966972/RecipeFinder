import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {toBase64String} from "@angular/compiler/src/output/source_map";

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.css']
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

  public steps: any[] = [{
    number: 1,
    details: ''
  }];

  addStep() {
    this.steps.push({
      number: this.steps.length + 1,
      details: '',
    });
  }


  ngOnInit(): void {
  }

  selectedFile1: File = null;
  picture1: string
  selectedFile2: File = null;
  picture2: string

  handleUpload1(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    this.selectedFile1 = <File>event.target.files[0];
    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.picture1 = reader.result.toString();
    };
  }
  handleUpload2(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    this.selectedFile2 = <File>event.target.files[0];
    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.picture2 = reader.result.toString();
    };
  }


  CreateRecipe() {
    let url = 'http://localhost:8080/recipe';
    let pictures = [{
      name : this.selectedFile1.name,
      type : this.selectedFile1.type,
      content : this.picture1.split(',')[1]
    },{
      name : this.selectedFile2.name,
      type : this.selectedFile2.type,
      content : this.picture2.split(',')[1]
    }]
    let token: string = '' + sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    let postData = {
      name: this.model.name,
      description: this.model.description,
      instructions: this.model.instructions,
      pictures: this.model.pictures,
      steps: this.steps     
    };
    this.http.post<Observable<boolean>>(url, postData, {headers: headers}).subscribe(isValid => {
      if (isValid) {
        console.log(postData);
        this.router.navigate(['']);
      } else {
        alert("User creation failed.")
      }

    });

  }

}
