import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "../../service/auth/auth.service";

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
    private http: HttpClient,
    private authService: AuthService,
  ) {
  }

  ngOnInit() {
  }

  register() {
    this.authService.register(this.credentials, () => {
      this.router.navigate(['']);
    })
  }
}
