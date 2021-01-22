import {Component} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

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

  checkIfAdmin() {
    let url = 'http://localhost:8080/session/login';
    let token: string = sessionStorage.getItem('token');
    let headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + token
    });

    let options = {headers: headers};
    this.http.get<Observable<Object>>(url, options).subscribe(principal => {

        if (principal['role'] == "USER") {
          alert('U bent niet geautoriseerd om deze pagina te bekijken.');
        } else {
          this.router.navigate(['/admin']);
        }

      },
      error => {
        if (error.status == 401)
          alert('U bent niet geautoriseerd om deze pagina te bekijken.');
      }
    );
  }
}
