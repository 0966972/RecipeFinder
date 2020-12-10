import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
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

  selectedFile: File = null;

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  onUpload(){
    let url = 'http://localhost:8080/picture';
    const fd = new FormData();
    console.log(this.selectedFile);
    console.log(this.selectedFile.name);
    fd.append('file',this.selectedFile, this.selectedFile.name);
    console.log(fd);
    this.http.post(url,fd).subscribe(res => {
      console.log(res);
    });
  }

  CreateRecipe() {
    let url = 'http://localhost:8080/recipe';
    // let pictures = [{
    //   name : this.model.pictures,
    //   content : this.model.pictures.binaryType
    // }]
    let token: string = '' + sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    let postData = {
      name: this.model.name,
      description: this.model.description,
      instructions: this.model.instructions
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
