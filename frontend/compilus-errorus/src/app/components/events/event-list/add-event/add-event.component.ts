import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Member } from '../../../../models/Member';
import { FlEvent } from '../../../../models/FlEvent';
import { EventsService } from '../../../../services/events.service';
import { EventListComponent } from '../event-list.component';
import { AuthService } from '../../../../services/auth.service';

@Component({
    selector: 'app-add-event',
    templateUrl: './add-event.component.html',
    styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
    @Output() eventAdded = new EventEmitter<FlEvent>();
    description: string;
    eventTitle: string;
    eventDate: Date;
    creator: Member;

    // tslint:disable-next-line:max-line-length
    constructor(private authService: AuthService,
                private eventService: EventsService,
                private eventListComponent: EventListComponent) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe(member => this.creator = member);
    }

    onSubmit() {
        const newEvent = new FlEvent();
        newEvent.description = this.description;
        newEvent.eventTitle = this.eventTitle;
        newEvent.eventDate = this.eventDate;
        newEvent.creator = this.creator;
        this.description = '';
        this.eventDate = null;
        this.eventTitle = '';
        this.eventService.saveEvent(newEvent).subscribe({
            complete: () => {
                this.eventListComponent.refreshEvents();
            }
        });
    }
}
