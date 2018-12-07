import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {TopnavComponent} from './topnav/topnav.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: 'parking/dashboard',
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
        RouterModule.forRoot(appRoutes),
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
