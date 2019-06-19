import { Component, OnInit } from '@angular/core';
import { Member } from '../../../../models/Member';
import { ProfileService } from '../../../../services/profile.service';

@Component({
    selector: 'app-about-me',
    templateUrl: './about-me.component.html',
    styleUrls: ['./about-me.component.css'],
    providers: [ ProfileService ]
})
export class AboutMeComponent implements OnInit {
    members: Member[];
    dummyMember: Member;

    constructor(private profileService: ProfileService) {
    }

    ngOnInit() {
        // this.getMembers();
        this.getDummy();
    }

    getMembers() {
        this.profileService.getMembers().subscribe(members => {
            this.members = members;
        });
    }

    getDummy() {
        this.profileService.getDummyMember().subscribe(member => {
            this.dummyMember = member;
        });
    }

}
