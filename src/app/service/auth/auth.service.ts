import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {finalize} from "rxjs/operators";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable()
export class AuthService {
  private readonly baseUrl = environment.apiUrl
  authenticated = false;
  role = 'USER';

  private userId: bigint = undefined;

  constructor(private http: HttpClient) {
  }

  get isAdmin(): boolean {
    return this.role == 'ADMIN';

  }

  get username(): string {
    let token: string = sessionStorage.getItem('token');
    if (token != '') {
      return atob(token).split(':')[0]
    }
    return '';
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    let url = this.baseUrl+'/auth/login';
    this.http.get<Observable<Object>>(url, {headers: headers}).subscribe(
      response => {
        if (response['name']) {
          this.authenticated = true;
          sessionStorage.setItem('token', btoa(credentials.username + ':' + credentials.password));
          console.log(response);
          if (response['principal']['user']['role'] == "ADMIN") {
            sessionStorage.setItem('role', 'admin');
            this.role = 'ADMIN';
          } else {
            sessionStorage.setItem('role', 'user');
            this.role = 'USER';
          }
        }
        return callback && callback();
      },
      (error) => {
        this.authenticated = false;
        sessionStorage.setItem('token', '')
        if (error.status == 403)
          alert("Je bent verbannen en kunt daarom niet inloggen")
        else
          alert("Deze combinatie van gebruikersnaam en wachtwoord is ongeldig.")
      }
    );
  }

  logout(callback) {
    const headers = new HttpHeaders({
      authorization: 'Basic ' + sessionStorage.getItem('token')
    });

    this.http.get(this.baseUrl+'/auth/logout', {headers: headers}).pipe(
      finalize(() => {
        this.authenticated = false;
        this.role = 'USER';
        sessionStorage.setItem('token', '');
        return callback && callback();
      })).subscribe();
  }

  getUserId(): bigint {
    let token: string = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    let url = this.baseUrl+'/auth/login';

    this.http.get<Observable<Object>>(url, {headers: headers}).subscribe(
      response => {
        if (response['name']) {
          console.log("retrieved user id: " + response['principal']['user']['id']);
          this.userId = response['principal']['user']['id'];
        }
      },
      (error) => {
        alert("Er ging iets mis, probeer het later nog eens")
      }
    );
    console.log("member: " + this.userId);
    return this.userId;
  }

  public getPrincipal(): Observable<Object> {
    let url = this.baseUrl+'/auth/login';
    const headers = new HttpHeaders({
      authorization: 'Basic ' + sessionStorage.getItem('token')
    });
    return this.http.get<Observable<Object>>(url, {headers: headers});
  }
}
