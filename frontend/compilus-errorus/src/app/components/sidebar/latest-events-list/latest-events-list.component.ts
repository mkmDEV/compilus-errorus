import { Component, OnInit } from '@angular/core';
import {FlEvent} from '../../../models/FlEvent';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-latest-events-list',
  templateUrl: './latest-events-list.component.html',
  styleUrls: ['./latest-events-list.component.css']
})
export class LatestEventsListComponent implements OnInit {
    events: FlEvent[];
  constructor(private eventService: EventService) { }

  ngOnInit() {
      this.getLatestEvents();
  }

    private getLatestEvents() {
      this.eventService.getLatestEvents().subscribe(events =>{
          this.events = events;
      });
    }
}
