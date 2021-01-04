import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AdminService} from "../service/admin.service";
import {AdminIngredient} from "../model/admin-ingredient";

@Component({
  selector: 'admin',
  templateUrl: './admin.component.html'
})

export class AdminComponent implements OnInit {
  loading: any;
  pendingIngredients: AdminIngredient[] = []
  rejectedIngredients: AdminIngredient[] = []

  constructor(
    private router: Router,
    private adminService: AdminService,
  ) {
  }


  acceptRejectedIngredient(i) {
    let ingredient = this.rejectedIngredients[i];

    this.adminService.acceptIngredient(ingredient).subscribe(ingredient => {
      if (ingredient)
        this.rejectedIngredients.splice(i, 1);
    });
  }


  acceptPendingIngredient(i) {
    let ingredient = this.pendingIngredients[i];

    this.adminService.acceptIngredient(ingredient).subscribe(it => {
      if (it)
        this.pendingIngredients.splice(i, 1);
    });
  }

  rejectPendingIngedient(i) {
    let ingredient = this.pendingIngredients[i];

    this.adminService.rejectIngredient(ingredient).subscribe(it => {
      if (it) {
        this.pendingIngredients.splice(i, 1);
        this.rejectedIngredients.push(it)
      }
    });
  }

  ngOnInit() {
    this.adminService.getPendingIngredients().subscribe(data => {
      this.pendingIngredients = data;
    });
    this.adminService.getRefusedIngredients().subscribe(data => {
      this.rejectedIngredients = data;
    });
  }
}
