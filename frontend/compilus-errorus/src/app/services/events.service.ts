import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {FlEvent} from '../models/FlEvent';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
    })
};

@Injectable({
    providedIn: 'root'
})
export class EventsService {
    eventsUrl = 'http://localhost:8080/events';
    latestEventsUrl = 'http://localhost:8080/events/latest';

    constructor(private http: HttpClient) {
    }

    getFlEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.eventsUrl, httpOptions);
    }

    getLatestEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.latestEventsUrl, httpOptions);
    }

    saveEvent(event: FlEvent): Observable<FlEvent> {
        return this.http.post<FlEvent>(this.eventsUrl, event, httpOptions);
    }

    updateEvent(event: FlEvent) {
        return this.http.put<FlEvent>(`${this.eventsUrl}/${event.id}`, event, httpOptions);
    }
}
