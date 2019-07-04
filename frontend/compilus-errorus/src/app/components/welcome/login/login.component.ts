import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Member } from '../../../models/Member';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    email = '';
    password = '';
    member = new Member();
    emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';

    constructor(private authService: AuthService, private modalService: NgbModal) {
    }

    ngOnInit() {
    }

    onLogin() {
        if (!this.isEmailValid()) {
            this.open();
            return;
        }

        this.member.email = this.email;
        this.member.password = this.password;
        this.authService.loginUser(this.member).subscribe((data) => {
            if (data.token) {
                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('email', data.email);
                sessionStorage.setItem('roles', data.roles.toString());
                this.email = '';
                this.password = '';
                location.assign('home');
            }
        });
    }

    isEmailValid() {
        return this.email.match(this.emailPattern);
    }

    open() {
        const modalRef = this.modalService.open(ModalComponent);
        modalRef.componentInstance.title = 'Invalid email';
        modalRef.componentInstance.message = 'Please enter a valid email address.';
    }
}
