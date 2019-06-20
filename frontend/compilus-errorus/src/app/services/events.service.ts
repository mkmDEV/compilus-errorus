import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest, HttpParams } from '@angular/common/http';
import {FlEvent} from '../models/FlEvent';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

@Injectable({
    providedIn: 'root'
})
export class EventsService {
    eventsUrl = 'http://localhost:8080/events';
    latestEventsUrl = 'http://localhost:8080/events/latest';

    constructor(private http: HttpClient) { }

    getFlEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.eventsUrl);
    }

    getLatestEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.latestEventsUrl);
    }

    saveEvent(event: FlEvent): Observable<FlEvent> {
        return this.http.post<FlEvent>(this.eventsUrl, event, httpOptions);
    }
}
