import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Review} from "../model/review.model";

@Component({
  selector: 'review-create',
  templateUrl: './review-create.component.html',
  styleUrls: ['./review-create.component.css']
})

export class ReviewCreateComponent implements OnInit {

  review: Review;
  showingPictures = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
    this.review = new Review(
       null,
      null,
      null,
      null,
      []
    )
  }

  ngOnInit() {
  }

  addPicture(event) {
    const reader = new FileReader();
    let file = event.target.files[0];
    if (file){
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

  get showPictures() : boolean {
    if (!this.showingPictures){
      this.showingPictures = true;
      return true;
    }
    else{
      this.showingPictures = false;
      return false;
    }
  }


  removePicture(index: number){
    this.review.pictures.splice(index, 1);
  }

  submitReview() {
  }
}
