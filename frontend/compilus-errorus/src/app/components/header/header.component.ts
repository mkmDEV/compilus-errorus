import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Member} from '../../models/Member';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
    loggedInMember = new Member();

    constructor(private router: Router, private authService: AuthService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( loggedInMember => {
            this.loggedInMember = loggedInMember;
        });
    }

    onLogout() {
        sessionStorage.clear();
        this.router.navigateByUrl('').then(() => console.log('logged out'));
    }

    goToMyProfile(id: string) {
        location.assign('/profile/' + id);
    }
}
