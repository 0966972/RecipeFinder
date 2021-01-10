import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";

import {HomeComponent} from './home/home.component';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {ProfileComponent} from './profile/profile.component';
import {RegisterComponent} from "./register/register.component";
import {AdminComponent} from "./admin/admin.component";
import {RecipeDetailsComponent} from "./recipe-details/recipe-details.component";
import {RecipeCreatorComponent} from './recipe-creator/recipe-creator.component';
import {AuthService} from "./service/auth.service";
import {RouterModule} from "@angular/router";
import {RecipeService} from "./service/recipe.service";
import {IngredientService} from "./service/ingredient.service";
import {NgbCarouselConfig, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RecipeIngredientService} from "./service/recipe-ingredient.service";
import {ReviewCreateComponent} from "./review-create/review-create.component";
import {ReportUserComponent} from "./report-user/report-user.component";
import {ReportService} from "./service/report.service";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    ProfileComponent,
    RegisterComponent,
    AdminComponent,
    RecipeDetailsComponent,
    RecipeCreatorComponent,
    ReviewCreateComponent,
    ReportUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    NgbModule,
  ],
  providers: [
    RecipeService,
    RecipeIngredientService,
    IngredientService,
    AuthService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    ReportService,
    NgbCarouselConfig
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
