import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Member } from '../../../models/Member';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    email = '';
    password = '';
    member = new Member();

    constructor(private authService: AuthService) {
    }

    ngOnInit() {
    }

    onLogin() {
        this.member.email = this.email;
        this.member.password = this.password;
        this.authService.loginUser(this.member).subscribe((data) =>  {
            sessionStorage.setItem('token', data.token);
            sessionStorage.setItem('email', data.email);
            sessionStorage.setItem('roles', data.roles.toString());
            this.email = '';
            this.password = '';
        });

    }
}
