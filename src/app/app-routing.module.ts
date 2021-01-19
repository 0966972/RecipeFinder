import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from "./profile/profile.component";
import {RegisterComponent} from "./register/register.component";
import {AdminComponent} from "./admin/admin.component";
import {RecipeDetailsComponent} from "./recipe-details/recipe-details.component";
import {RecipeCreatorComponent} from "./recipe-creator/recipe-creator.component";
import {ReviewCreateComponent} from "./review-create/review-create.component";
import {ReportUserComponent} from "./report-user/report-user.component";
import {FavoritesComponent} from "./favorites/favorites.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminComponent},
  {
    path: 'recipe', children: [
      {
        path: ':id', component: RecipeDetailsComponent, children: [
          {path: 'review-create', component: ReviewCreateComponent}
        ]
      }
    ]
  },
  {path: 'user/:id1/favorites/:id2', component: FavoritesComponent},
  {path: 'recipe-creator', component: RecipeCreatorComponent},
  {path: 'report-user', component: ReportUserComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
