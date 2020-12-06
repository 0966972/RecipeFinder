import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
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
  ) {
  }

  ngOnInit() {
  }
}
