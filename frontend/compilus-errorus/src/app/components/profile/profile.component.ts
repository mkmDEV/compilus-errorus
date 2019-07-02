import { Component, OnInit } from '@angular/core';
import { Member } from '../../models/Member';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    member = new Member();

    constructor(private authService: AuthService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( member => this.member = member);
    }

}
