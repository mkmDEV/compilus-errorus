import { Component, Input, OnInit } from '@angular/core';
import { FlEvent } from '../../../../models/FlEvent';

@Component({
    selector: 'app-latest-events-item',
    templateUrl: './latest-events-item.component.html',
    styleUrls: ['./latest-events-item.component.css']
})
export class LatestEventsItemComponent implements OnInit {
    @Input() event: FlEvent;

    constructor() {
    }

    ngOnInit() {
    }

}
