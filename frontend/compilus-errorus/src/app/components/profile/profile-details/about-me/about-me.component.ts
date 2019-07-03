import { Component, OnInit } from '@angular/core';
import { Member } from '../../../../models/Member';
import { ProfileService } from '../../../../services/profile.service';
import { AuthService } from '../../../../services/auth.service';

@Component({
    selector: 'app-about-me',
    templateUrl: './about-me.component.html',
    styleUrls: ['./about-me.component.css'],
    providers: [ProfileService]
})
export class AboutMeComponent implements OnInit {
    member = new Member();

    constructor(private authService: AuthService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( loggedInMember => {
            this.member = loggedInMember;
        });
    }


}
