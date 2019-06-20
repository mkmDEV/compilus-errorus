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
    postsUrl = 'http://localhost:8080/events';
    latestEventsUrl = 'http://localhost:8080/events/latest';

    constructor(private http: HttpClient) { }

    getFlEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.postsUrl);
    }

    getLatestEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.latestEventsUrl);
    }
}
