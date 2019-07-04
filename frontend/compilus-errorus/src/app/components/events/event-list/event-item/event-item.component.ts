import {Component, Input, OnInit} from '@angular/core';
import {FlEvent} from '../../../../models/FlEvent';
import {Member} from '../../../../models/Member';
import {EventsService} from '../../../../services/events.service';
import {AuthService} from '../../../../services/auth.service';
import { ModalComponent } from '../../../modal/modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-event-item',
    templateUrl: './event-item.component.html',
    styleUrls: ['./event-item.component.css']
})
export class EventItemComponent implements OnInit {
    @Input() event: FlEvent;
    loggedInMember: Member;

    constructor(private eventsService: EventsService,
                private authService: AuthService,
                private modalService: NgbModal) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe((loggedInMember) => this.loggedInMember = loggedInMember);
    }

    onJoin() {
        if (this.event.participants.filter(value => value.email === this.loggedInMember.email).length === 0) {
            this.event.participants.push(this.loggedInMember);
            this.eventsService.updateEvent(this.event).subscribe( () => this.open());
        }
    }

    goToProfile(id: string) {
        location.assign('/profile/' + id);
    }

    open() {
        const modalRef = this.modalService.open(ModalComponent);
        modalRef.componentInstance.title = 'Success';
        modalRef.componentInstance.message = `You have joined the ${this.event.eventTitle}!`;
    }
}
