import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {FavoritesListService} from "../service/favorites-list.service";
import {favorite} from "../model/favorite.model";

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  model: any = {};
  loading: any;
  favorites: any;
  favorite: favorite;

  user = {
    id: 0,
    username: '',
    role: '',
  }

  addList: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private favoritesListService: FavoritesListService,
  ) {
    this.favorite = new favorite(
    )
  }

  ngOnInit() {
    let url = 'http://localhost:8080/auth/login';
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
          this.http.get('http://localhost:8080/user/'+this.user.id+'/favorites').subscribe(Response => this.favorites = Response);
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

  openRecipe(id: number) {
    console.log(this.user.id);
    console.log(id);
    console.log(this.router.navigate(['user/' + this.user.id + '/favorites/' + id]))
    this.router.navigate(['user/' + this.user.id + '/favorites/' + id])
  }
  addFavorite(){
    this.addList=true;
  }
  submitReview() {
    let url = 'http://localhost:8080/user/' + this.user.id + '/favorites';
    let token: string = '' + sessionStorage.getItem('token');
    let body = this.favorite
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });
    this.http.post<Observable<favorite>>(url, body, {headers: headers}).subscribe()
    this.favorite.name = '';
    this.addList = false;
    this.ngOnInit();
  }

}
