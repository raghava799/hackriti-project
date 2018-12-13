import {Component, OnDestroy} from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
    selector: 'app-top-nav',
    templateUrl: 'topnav.component.html'
})
export class TopnavComponent implements OnDestroy {
    public user: Object;
    public role: string;

    constructor(private authService: AuthService) {
        if (sessionStorage.getItem('admin')) {
            this.user = JSON.parse(sessionStorage.getItem('admin'));
        }
    }

    roleEvent = this.authService.role.subscribe(role => {
        if (role) {
            this.role = role;
        }
    });

    logout() {
        this.authService.removeToken();
        this.authService.goToLoginPage();
    }

    ngOnDestroy(): void {
        this.roleEvent.unsubscribe();
    }
}
