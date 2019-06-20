import {HttpClient, HttpEvent, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {FlEvent} from '../models/FlEvent';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

@Injectable({
    providedIn: 'root'
})
export class EventService {
    latestEventsUrl = 'http://localhost:8080/events/latest';


    constructor(private http: HttpClient) {
    }

    getLatestEvents(): Observable<FlEvent[]> {
        return this.http.get<FlEvent[]>(this.latestEventsUrl);
    }
}
