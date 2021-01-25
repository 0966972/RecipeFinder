import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";

import {HomeComponent} from './component/home/home.component';
import {AppComponent} from './app.component';
import {LoginComponent} from './component/login/login.component';
import {ProfileComponent} from './component/profile/profile.component';
import {RegisterComponent} from "./component/register/register.component";
import {AdminComponent} from "./component/admin/admin.component";
import {RecipeDetailsComponent} from "./component/recipe-details/recipe-details.component";
import {RecipeCreatorComponent} from './component/recipe-creator/recipe-creator.component';
import {AuthService} from "./service/auth/auth.service";
import {RouterModule} from "@angular/router";
import {RecipeService} from "./service/recipe/recipe.service";
import {IngredientService} from "./service/ingredient/ingredient.service";
import {NgbCarouselConfig, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RecipeIngredientService} from "./service/recipe-ingredient/recipe-ingredient.service";
import {ReviewCreateComponent} from "./component/review-create/review-create.component";
import {ReportUserComponent} from "./component/report-user/report-user.component";
import {ReportService} from "./service/report/report.service";
import {FavoritesComponent} from './component/favorites/favorites.component';
import {ClipboardModule} from "@angular/cdk/clipboard";
import {WarningService} from "./service/warning/warning.service";
import {WarnUserComponent} from "./component/warn-user/warn-user.component";

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
    ReportUserComponent,
    WarnUserComponent,
    FavoritesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    NgbModule,
    ClipboardModule
  ],
  providers: [
    RecipeService,
    RecipeIngredientService,
    IngredientService,
    AuthService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    ReportService,
    WarningService,
    NgbCarouselConfig
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
