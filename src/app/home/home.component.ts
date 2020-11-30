import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  title = 'RecipeFinder';

  constructor(
    private http: HttpClient,
    private router: Router
    ) { }

  ngOnInit() {}

  logout() {
    sessionStorage.setItem('token', '');
    alert('You have been successfully logged out.');
  }
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };

  checkIfAdmin() {
    let url = 'http://localhost:8080/user';
    let token : string = sessionStorage.getItem('token');
    if (token != null && token != '') {
      let headers: HttpHeaders = new HttpHeaders({
        'Authorization': 'Basic ' + token
      });

      let options = { headers: headers };
      this.http.get<Observable<Object>>(url, options).
      subscribe(principal => {

          if (principal['role'] == "USER"){
            alert('Please login as an admin to view this page.');
          }
          else{
            this.router.navigate(['/admin']);
          }

        },
        error => {
          if(error.status == 401)
            alert('Unauthorized, unable to retrieve user info.');
        }
      );
    }
    else{
      alert('Please login as an admin to view this page.');
    }
  }
}
