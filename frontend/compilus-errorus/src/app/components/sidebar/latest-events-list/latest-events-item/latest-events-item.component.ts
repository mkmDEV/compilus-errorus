import {Component, Input, OnInit } from '@angular/core';
import {FlEvent} from '../../../../models/FlEvent';
import {Member} from '../../../../models/Member';
import {AuthService} from '../../../../services/auth.service';
import {EventsService} from '../../../../services/events.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../../modal/modal.component';

@Component({
    selector: 'app-latest-events-item',
    templateUrl: './latest-events-item.component.html',
    styleUrls: ['./latest-events-item.component.css']
})
export class LatestEventsItemComponent implements OnInit {
    @Input() event: FlEvent;
    loggedInMember: Member;

    constructor(private eventsService: EventsService,
                private authService: AuthService,
                private modalService: NgbModal
    ) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe((loggedInMember) => this.loggedInMember = loggedInMember);
    }


    onJoin() {
        this.event.participants.push(this.loggedInMember);
        this.eventsService.updateEvent(this.event).subscribe(() => this.open());
    }

    open() {
        const modalRef = this.modalService.open(ModalComponent);
        modalRef.componentInstance.title = 'Success';
        modalRef.componentInstance.message = `You have joined the ${this.event.eventTitle}!`;
    }

}
