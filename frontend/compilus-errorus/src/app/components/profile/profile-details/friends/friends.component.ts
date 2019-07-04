import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../../../../services/profile.service';
import { Member } from '../../../../models/Member';
import { AuthService } from '../../../../services/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
    member = new Member();
    friends: Member[];
    id: string;

    constructor(private authService: AuthService, private profileService: ProfileService, private route: ActivatedRoute) {
        this.route.params.subscribe( params => this.id = params.id);
    }

    ngOnInit() {
        this.member.id = this.id;
        this.authService.getMember(this.member).subscribe( member => {
            this.member = member;
            this.getFriends(this.member);
        });
    }

    getFriends(member: Member) {
        this.profileService.getFriends(member).subscribe(friends => {
            this.friends = friends;
        });
    }

    goToProfile(id: string) {
        location.assign('/profile/' + id);
    }
}
