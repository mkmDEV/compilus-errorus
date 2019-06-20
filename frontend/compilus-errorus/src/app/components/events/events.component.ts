import { Component, OnInit } from '@angular/core';
import { FlEvent } from '../../models/FlEvent';
import { EventsService } from '../../services/events.service';

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
    flEvents: FlEvent[];

    constructor() { }

    ngOnInit() {
        this.getEvents();
    }

    private getEvents() {

    }
}
