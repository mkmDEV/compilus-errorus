import { Component, Input, OnInit } from '@angular/core';
import { FlEvent } from '../../../../models/FlEvent';

@Component({
    selector: 'app-event-item',
    templateUrl: './event-item.component.html',
    styleUrls: ['./event-item.component.css']
})
export class EventItemComponent implements OnInit {
    @Input() event: FlEvent;

    constructor() {
    }

    ngOnInit() {
    }

}
