import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from "./profile/profile.component";
import {RegisterComponent} from "./register/register.component";
import {AdminComponent} from "./admin/admin.component";
import {RecipeDetailsComponent} from "./recipe-details/recipe-details.component";
import {RecipeCreatorComponent} from "./recipe-creator/recipe-creator.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminComponent},
  {
    path: 'recipe', children: [
      {path: ':id', component: RecipeDetailsComponent}
    ]
  },
  {path: 'recipe-creator', component: RecipeCreatorComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
