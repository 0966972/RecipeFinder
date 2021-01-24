import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Review} from "../../model/review.model";
import {Observable} from "rxjs";
import {AuthService} from "../../service/auth/auth.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'review-create',
  templateUrl: './review-create.component.html',
  styleUrls: ['./review-create.component.css']
})

export class ReviewCreateComponent implements OnInit {
  private readonly baseUrl = environment.apiUrl
  routeId: number;
  score=[true,false,false,false,false]
  review: Review;
  showingPictures = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.review = new Review(
      null,
      1,
      null,
      null,
      []
    )
  }

  public showTitleError = false;
  public showMessageError = false;

  get isLoggedIn(): boolean {
    return this.authService.authenticated;
  }

  get showPictures(): boolean {
    if (!this.showingPictures) {
      this.showingPictures = true;
      return true;
    } else {
      this.showingPictures = false;
      return false;
    }
  }

  ngOnInit() {
    console.log(this.route.parent.url.subscribe(urlSegment => {
      this.routeId = parseInt(urlSegment[0]['path']);
    }));
  }

  scoreSelected(i) {
    this.review.score = i + 1;
    this.score = []
    for (let j = 0; j < 5; j++)
      this.score.push(j <= i);
  }

  addPicture(event) {
    const reader = new FileReader();
    let file = event.target.files[0];
    if (file) {
      reader.readAsDataURL(file);
      reader.onload = () => {
        let picture = {
          name: file.name,
          type: file.type,
          content: reader.result.toString().split(',')[1]
        }
        this.review.pictures.push(picture)
      };
    }
  }

  removePicture(index: number) {
    this.review.pictures.splice(index, 1);
  }

  submitReview() {
    if (this.validateInput()) {
      this.sendHttpRequest();
    }
  }

  validateInput() : boolean{
    if (this.validateString(this.review.title)){
      this.showTitleError = true;
      return false;
    }else{
      this.showTitleError = false;
    }
    if (this.validateString(this.review.message)){
      this.showMessageError = true;
      return false;
    }else{
      this.showMessageError = false;
    }

    return true;
  }

  validateString(string: string) {
    return !string || !string.trim() || string == '';
  }

  sendHttpRequest() {
    let url = this.baseUrl+'/recipe/' + this.routeId + '/review';
    let token: string = '' + sessionStorage.getItem('token');
    let body = this.review
    const headers = new HttpHeaders({
      authorization: 'Basic ' + token
    });

    this.http.post<Observable<Review>>(url, body, {headers: headers}).subscribe(() => {
      this.navigate();
    }, error => {
      this.displayHttpError(error.status);
    });
  }

  navigate() {
    let previousRoute = this.route.snapshot.paramMap.get('previous');
    if (previousRoute) {
      this.router.navigate([previousRoute]);
    } else {
      this.router.navigate(['']);
    }
  }

  displayHttpError(status: any) {
    switch (status) {
      case 400:
        alert("400: De beoordeling heeft ongeldige waarden.")
        break;
      default:
        alert("Er ging iets mis, probeer het later nog eens.")
        break;
    }
  }
}
