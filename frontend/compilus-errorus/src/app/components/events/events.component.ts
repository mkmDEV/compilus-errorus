import {Component, OnInit} from '@angular/core';
import {FlEvent} from '../../models/FlEvent';
import {EventsService} from '../../services/events.service';
import {Member} from '../../models/Member';

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
    constructor(private eventService: EventsService) {
    }

    ngOnInit() {
    }

    /*onAdded(newEvent: { description: string, eventTitle: string, eventDate: Date, creator: Member }) {
        const event = new FlEvent();
        event.description = newEvent.description;
        event.eventTitle = newEvent.eventTitle;
        event.eventDate = newEvent.eventDate;
        event.creator = newEvent.creator;
        this.eventService.saveEvent(event).subscribe({complete: () => this.eventListComponent.refreshEvents()});
    }*/
}
