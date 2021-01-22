import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  private readonly baseUrl = environment.apiUrl
  credentials = {username: '', password: ''};
  loading: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
  }

  register() {
    let url = this.baseUrl+'/user';
    let token: string = '' + sessionStorage.getItem('token');
    let body
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });

    this.http.post<Observable<Object>>(url, {
      username: this.credentials.username,
      password: this.credentials.password
    }).subscribe(() => {
      sessionStorage.setItem('token', btoa(this.credentials.username + ':' + this.credentials.password));
      this.router.navigate(['']);
    }, error => {
      if (error.status == 409) {
        alert("Een gebruiker met deze naam bestaat al.")
      } else {
        alert("Er ging iets mis, probeer het later nog eens.")
      }
    });
  }
}
