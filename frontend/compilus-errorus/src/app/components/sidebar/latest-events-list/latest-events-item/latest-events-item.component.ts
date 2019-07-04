import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FlEvent} from '../../../../models/FlEvent';
import {Member} from '../../../../models/Member';
import {AuthService} from '../../../../services/auth.service';
import {EventsService} from '../../../../services/events.service';

@Component({
    selector: 'app-latest-events-item',
    templateUrl: './latest-events-item.component.html',
    styleUrls: ['./latest-events-item.component.css']
})
export class LatestEventsItemComponent implements OnInit {
    @Input() event: FlEvent;
    loggedInMember: Member;

    constructor(private eventsService: EventsService,
                private authService: AuthService
    ) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe((loggedInMember) => this.loggedInMember = loggedInMember);
    }


    onJoin() {
        this.event.participants.push(this.loggedInMember);
        this.eventsService.updateEvent(this.event).subscribe();
    }

}
