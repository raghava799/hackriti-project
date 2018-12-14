import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TopnavComponent} from './topnav/topnav.component';
import {AuthInterceptor} from './services/auth-interceptor';
import {LoginComponent} from './login/login.component';
import {materialModule} from './material.module';
import {AuthenticationService} from './services/authentication.service';
import {GlobalService} from './services/global.service';
import {AuthService} from './services/auth.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpService} from './httpService';
import {AlertComponent} from './alert/alert.component';
import {AlertService} from './alert/alert.service';


const appRoutes: Routes = [

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'parking',
        component: TopnavComponent,
        children: [
            {
                path: 'dashboard',
                component: DashboardComponent
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'login',
        pathMatch: 'full'
    }

];

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        DashboardComponent,
        TopnavComponent,
        AlertComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        materialModule,
        RouterModule.forRoot(appRoutes)
    ],
    providers: [AuthenticationService,
        GlobalService,
        AuthService,
        HttpService,
        AlertService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        }],
    bootstrap: [AppComponent]
})
export class AppModule {
}
