import { Component, OnInit } from '@angular/core';
import { Member } from '../../../../models/Member';
import { ProfileService } from '../../../../services/profile.service';
import { AuthService } from '../../../../services/auth.service';
import {ActivatedRoute} from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../../modal/modal.component';

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

    constructor(private authService: AuthService,
                private route: ActivatedRoute,
                private profileService: ProfileService,
                private modalService: NgbModal) {
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
        if (!this.isUserSame()) {
            this.profileService.updateMember(this.loggedInMember, this.member).subscribe( () => this.open());
        }
    }

    isUserSame() {
        return this.member.email === sessionStorage.getItem('email');
    }

    open() {
        const modalRef = this.modalService.open(ModalComponent);
        modalRef.componentInstance.title = 'Success';
        modalRef.componentInstance.message = `Now you are friends with ${this.member.name}.`;
    }
}
