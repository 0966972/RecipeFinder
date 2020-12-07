import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html'
})

export class ProfileComponent implements OnInit {

  model: any = {};
  loading: any;

  user = {
    id: 0,
    username: '',
    role: '',
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
    let url = 'http://localhost:8080/session/login';
    let token: string = sessionStorage.getItem('token');
    if (token != null && token != '') {
      let headers: HttpHeaders = new HttpHeaders({
        'Authorization': 'Basic ' + token
      });


      let options = {headers: headers};
      this.http.get<Observable<Object>>(url, options).subscribe(response => {
          this.user.username = response['name'];
          this.user.id = response['principal']['user']['id'];
          this.user.role = response['principal']['user']['role'];
        },
        error => {
          if (error.status == 401)
            alert('U bent niet geautoriseerd om deze pagina te bekijken.');
          this.router.navigate(['']);
        }
      );
    } else {
      this.router.navigate(['/login']);
      alert('U dient ingelogd te zijn om uw profiel te bekijken.');
    }
  }
}
