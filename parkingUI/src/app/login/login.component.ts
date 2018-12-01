import {Component, OnInit} from '@angular/core';
import * as APP_CONST from '../constants/app.const';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {
  constructor() {
  }

  ngOnInit() {
  }

  onSubmit() {
    window.open(APP_CONST.AUTHORIZATION, '_self');
  }
}
