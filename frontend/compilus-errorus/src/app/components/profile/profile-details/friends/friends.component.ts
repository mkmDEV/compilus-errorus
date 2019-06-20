import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../../../../services/profile.service';
import { Member } from '../../../../models/Member';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
    friends: Member[];

    constructor(private profileService: ProfileService) {
    }

    ngOnInit() {
        this.getFriends();
    }

    getFriends() {
        this.profileService.getFriends().subscribe(friends => {
            this.friends = friends;
        });
    }
}
