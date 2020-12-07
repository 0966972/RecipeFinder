import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'admin',
  templateUrl: './admin.component.html'
})

export class AdminComponent implements OnInit {
  loading: any;
  isAdmin = false;

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

  get isContentVisible(): boolean {
    return this.isAdmin;
  }

  ngOnInit() {
    let token: string = sessionStorage.getItem('token');
    let url = 'http://localhost:8080/session/login';
    let headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + token
    });

    let options = {headers: headers};
    this.http.get<Observable<Object>>(url, options).subscribe(response => {
        this.user.username = response['name'];
        this.user.id = response['principal']['user']['id'];
        this.user.role = response['principal']['user']['role'];

        if (this.user.role == "USER") {
          alert('U bent niet geautoriseerd om deze pagina te bekijken.');
          this.router.navigate(['']);
          this.isAdmin = false;
        }
        this.isAdmin = true;
      },
      error => {
        if (error.status == 401) {
          alert('U bent niet geautoriseerd om deze pagina te bekijken.');
          this.router.navigate(['']);
          this.isAdmin = false;
        }
      }
    );
  }
}
