import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'login',
  templateUrl: './register.component.html'
})

export class RegisterComponent implements OnInit {

  model: any = {};
  loading: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  register() {
    let url = 'http://localhost:8080/users';
    this.http.post<Observable<boolean>>(url, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(isValid => {
      if (isValid) {
        sessionStorage.setItem('token', btoa(this.model.username + ':' + this.model.password));
        this.router.navigate(['']);
      } else {
        alert("User creation failed.")
      }
    });
  }
}
