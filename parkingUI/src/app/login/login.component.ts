import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {AuthenticationService} from '../services/authentication.service';
import {GlobalService} from '../services/global.service';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  admin: any;
  private  _name;
  loading = false;
  returnUrl: string;
  googleUrl = GlobalService.getGoogleLoginURL();
  code;

  isLoggedIn = false;
  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private globalService: GlobalService) {
  }

  ngOnInit() {
    if (GlobalService.getToken()) {
      this.isLoggedIn = true;
    }
    this.returnUrl =  this.activatedRoute.snapshot.queryParams['returnUrl'] || '/';

    if (this.isLoggedIn) {
      this.loading = true;
      this.authenticationService.getMyself().then( res => {
        this.admin = res;
        this.loading = false;
      }).catch(err => {
        this.globalService.handleError(err);
        this.loading = false;
      });
    } else {
      this.activatedRoute.queryParams.subscribe(params => {
        this.code = params['code'];
        console.log(this.code);
        if (this.code != null) {
          this.login();
        }
      });
    }
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.code)
      .then( res => {
        console.log(res);
        const token = res.headers.get('Authorization');
        console.log(token);
        if (token) {
          GlobalService.setToken(token);
        }
          this.admin = res['body'];

        this.isLoggedIn = true;
        console.log('logged in');
        this.loading = false;
      })
      .catch( error => {
        console.log(error);
        if (error.status === 406) {
          console.log('Please enter valid mail id or password');
        } else {
          error.status > 399 || error.status < 500 ? console.log('Authentication Failed!!!') :
           console.log(error);
        }
        this.isLoggedIn = false;
      this.loading = false;
        console.log('Could not logged in');
    });
    this.code = null;
  }

  getUserName(): string {
    if (!this._name && (this.admin && this.admin.name)) {
      // this._name = this.admin.emailId.replace(/(.*)\.(.*)@(.*)/, '$1 $2');
      this._name = this.admin.name;
    }
    if (this._name) {
      return this._name;
    } else {
      return 'User';
    }
  }

  goToSite() {
    this.router.navigate(['/parking/dashboard']);
  }

}
