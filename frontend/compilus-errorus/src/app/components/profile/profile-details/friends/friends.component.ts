import { Component, Input, OnInit } from '@angular/core';
import { ProfileService } from '../../../../services/profile.service';
import { Member } from '../../../../models/Member';
import { AuthService } from '../../../../services/auth.service';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
    member = new Member();
    friends: Member[];

    constructor(private authService: AuthService, private profileService: ProfileService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( loggedInMember => {
            this.member = loggedInMember;
            this.getFriends();
        });
    }

    getFriends() {
        this.profileService.getFriends(this.member).subscribe(friends => {
            this.friends = friends;
        });
    }
}
