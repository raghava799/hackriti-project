import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {
  constructor(private fb: FormBuilder) {
  }
  public loginForm: FormGroup;
  ngOnInit() {
    this.loginForm = this.fb.group({
      userName: ['USERNAME'],
      password: ['']
    });
  }
}
