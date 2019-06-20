import { Component, OnInit } from '@angular/core';
import {FlEvent} from '../../../models/FlEvent';
import {EventsService} from '../../../services/events.service';

@Component({
  selector: 'app-latest-events-list',
  templateUrl: './latest-events-list.component.html',
  styleUrls: ['./latest-events-list.component.css']
})
export class LatestEventsListComponent implements OnInit {
    events: FlEvent[];
  constructor(private eventsService: EventsService) { }

  ngOnInit() {
      this.getLatestEvents();
  }

    private getLatestEvents() {
      this.eventsService.getLatestEvents().subscribe(events => {
          this.events = events;
      });
    }
}
