import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Member } from '../models/Member';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
    url = 'http://localhost:8080/register';

  constructor(private http: HttpClient) { }

    registerUser(member: Member) {
        return this.http.post<Member>(this.url, member, httpOptions);
    }
}
