import { Component, OnInit } from '@angular/core';
import { Member } from '../../../../models/Member';
import { ProfileService } from '../../../../services/profile.service';
import { AuthService } from '../../../../services/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-about-me',
    templateUrl: './about-me.component.html',
    styleUrls: ['./about-me.component.css'],
    providers: [ProfileService]
})
export class AboutMeComponent implements OnInit {
    loggedInMember = new Member();
    member = new Member();
    id: string;

    constructor(private authService: AuthService, private route: ActivatedRoute, private profileService: ProfileService) {
        this.route.params.subscribe( params => this.id = params.id);
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( loggedInMember => {
            this.loggedInMember = loggedInMember;
        });
        this.member.id = this.id;
        this.authService.getMember(this.member).subscribe(member => {
            this.member = member;
        });
    }

    onAddFriend() {
        this.profileService.updateMember(this.loggedInMember, this.member).subscribe(() => console.log('update sent'));
    }
}
