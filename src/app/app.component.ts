import {Component} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'RecipeFinder';

  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {
  }

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  get username(): string {
    return this.authService.username;
  }

  get isAdmin(): boolean {
    return this.authService.isAdmin;
  }

  logout() {
    this.authService.logout(() => {
      this.router.navigate[''];
    })
  };
}
