import {Component} from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
    selector: 'app-top-nav',
    templateUrl: 'topnav.component.html'
})
export class TopnavComponent {
    constructor(private authService: AuthService) {
    }

    logout() {
        this.authService.removeToken();
        this.authService.goToLoginPage();
    }
}
