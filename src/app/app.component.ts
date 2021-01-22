import {Component} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'RecipeFinder';

  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {
  }

  logout() {
    this.authService.logout(() => {
      this.router.navigate[''];
    })
  };

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  get username(): string {
    return this.authService.username;
  }

  get isAdmin(): boolean {
    return this.authService.isAdmin;
  }
}
