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
export class AuthService {
    url = 'http://localhost:8080/login';

    constructor(private http: HttpClient) {
    }

    loginUser(member: Member) {
        return this.http.post<{roles: [], email: string, token: string}>(this.url, member, httpOptions);
    }
}
