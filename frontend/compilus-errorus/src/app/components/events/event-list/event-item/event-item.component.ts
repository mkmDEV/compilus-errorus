import {Component, Input, OnInit} from '@angular/core';
import {FlEvent} from '../../../../models/FlEvent';
import {Member} from '../../../../models/Member';
import {EventsService} from '../../../../services/events.service';
import {AuthService} from '../../../../services/auth.service';

@Component({
    selector: 'app-event-item',
    templateUrl: './event-item.component.html',
    styleUrls: ['./event-item.component.css']
})
export class EventItemComponent implements OnInit {
    @Input() event: FlEvent;
    loggedInMember: Member;

    constructor(private eventsService: EventsService,
                private authService: AuthService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe((loggedInMember) => this.loggedInMember = loggedInMember);
    }

    onJoin() {
        this.event.participants.push(this.loggedInMember);
        this.eventsService.updateEvent(this.event).subscribe();
    }

    goToProfile(id: string) {
        location.assign('/profile/' + id);
    }
}
