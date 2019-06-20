import { Component, OnInit } from '@angular/core';
import {FlEvent} from '../../../models/FlEvent';
import {EventsService} from '../../../services/events.service';

@Component({
    selector: 'app-event-list',
    templateUrl: './event-list.component.html',
    styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {
    flEvents: FlEvent[];

    constructor(private eventService: EventsService) { }

    ngOnInit() {
        this.getEvents();
    }

    private getEvents() {
        this.eventService.getFlEvents().subscribe( events => {
            this.flEvents = events;
        });
    }

}
