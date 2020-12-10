import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {toBase64String} from "@angular/compiler/src/output/source_map";

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
  test: string

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
  }
  handleUpload(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.test = reader.result.toString();
    };
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
    let pictures = [{
      name : 'test',
      content : this.test.split(',')[1]
    }]
    let token: string = '' + sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    let postData = {
      name: this.model.name,
      description: this.model.description,
      instructions: this.model.instructions,
      pictures
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
