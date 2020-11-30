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

  id: bigint;
  userName: string;
  role: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit() {
    let url = 'http://localhost:8080/user';
    let token : string = sessionStorage.getItem('token');
    if (token != null && token != '') {
      let headers: HttpHeaders = new HttpHeaders({
        'Authorization': 'Basic ' + token
      });


      let options = { headers: headers };
      this.http.get<Observable<Object>>(url, options).
      subscribe(principal => {
          this.userName = principal['username'];
          this.id = principal['id'];
          this.role = principal['role'];
        },
        error => {
          if(error.status == 401)
            alert('Unauthorized, unable to retrieve user info.');
          this.router.navigate(['']);
        }
      );
    }
    else{
      this.router.navigate(['/login']);
      alert('Please login to view your profile.');
    }
  }
}
