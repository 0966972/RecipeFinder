import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./component/login/login.component";
import {HomeComponent} from './component/home/home.component';
import {ProfileComponent} from "./component/profile/profile.component";
import {RegisterComponent} from "./component/register/register.component";
import {AdminComponent} from "./component/admin/admin.component";
import {RecipeDetailsComponent} from "./component/recipe-details/recipe-details.component";
import {RecipeCreatorComponent} from "./component/recipe-creator/recipe-creator.component";
import {ReviewCreateComponent} from "./component/review-create/review-create.component";
import {ReportUserComponent} from "./component/report-user/report-user.component";
import {FavoritesComponent} from "./component/favorites/favorites.component";
import {WarnUserComponent} from "./component/warn-user/warn-user.component";

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
  {
    path: 'warn-user', children: [
      {path: ':id', component: WarnUserComponent}
    ]
  },
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
