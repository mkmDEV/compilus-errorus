import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Member} from '../../../../models/Member';
import {ProfileService} from '../../../../services/profile.service';
import {FlEvent} from '../../../../models/FlEvent';
import {EventsService} from '../../../../services/events.service';
import {EventListComponent} from '../event-list.component';

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
    // newEvent = {description: '', eventTitle: '', eventDate: null, creator: null};

    // tslint:disable-next-line:max-line-length
    constructor(private profileService: ProfileService, private eventService: EventsService, private eventListComponent: EventListComponent) { }

    ngOnInit() {
        this.profileService.getDummyMember().subscribe(member => this.creator = member);
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
        this.eventService.saveEvent(newEvent).subscribe( {complete: () => {
            this.eventListComponent.refreshEvents();
            }} );
    }
}
