import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TopnavComponent} from './topnav/topnav.component';
import {AuthInterceptor} from './login/auth-interceptor';
import {LoginComponent} from './login/login.component';
import {DemoMaterialModule} from './login/demo-material.module';
import {AuthenticationService} from './login/authentication.service';
import {GlobalService} from './login/global.service';
import {AuthService} from './login/auth.service';


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
        TopnavComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        DemoMaterialModule,
        RouterModule.forRoot(appRoutes)
    ],
    providers: [AuthenticationService,
        GlobalService,
        AuthService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        }],
    bootstrap: [AppComponent]
})
export class AppModule {
}
