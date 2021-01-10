import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AdminService} from "../service/admin.service";
import {AdminIngredient} from "../model/admin-ingredient";
import {Report} from "../model/report.model";
import {Recipe} from "../model/recipe";
import {User} from "../model/user.model";
import {ReportService} from "../service/report.service";

@Component({
  selector: 'admin',
  templateUrl: './admin.component.html'
})

export class AdminComponent implements OnInit {
  loading: any;
  pendingIngredients: AdminIngredient[] = []
  rejectedIngredients: AdminIngredient[] = []
  reports: Report[] = []

  constructor(
    private router: Router,
    private adminService: AdminService,
    private reportService: ReportService,
  ) {
  }


  banReportedUser(reportedUser: User, i) {
    this.adminService.banUser(reportedUser).subscribe(result => {
      if (result)
        this.reports.splice(i, 1);
    });
  }


  viewRecipe(recipe: Recipe, i) {
    this.router.navigate(['recipe/' + recipe.id])
  }


  acceptRejectedIngredient(ingredient: AdminIngredient, i) {
    this.adminService.acceptIngredient(ingredient).subscribe(ingredient => {
      if (ingredient)
        this.rejectedIngredients.splice(i, 1);
    });
  }


  acceptPendingIngredient(ingredient: AdminIngredient, i) {
    this.adminService.acceptIngredient(ingredient).subscribe(it => {
      if (it)
        this.pendingIngredients.splice(i, 1);
    });
  }

  rejectPendingIngedient(ingredient: AdminIngredient, i) {
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
    this.reportService.getReports().subscribe(data => {
      this.reports = data;
    })
  }
}
