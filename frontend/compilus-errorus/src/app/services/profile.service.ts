import { Injectable } from '@angular/core';
import { Member } from '../models/Member';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
    })
};

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    private memberUrl = 'http://localhost:8080/members';
    dummyMemberUrl = 'http://localhost:8080/members/dummy-member';

    constructor(private http: HttpClient) {
    }

    getFriends(): Observable<Member[]> {
        return this.http.get<Member[]>(this.memberUrl, httpOptions);
    }

    getDummyMember(): Observable<Member> {
        return this.http.get<Member>(this.dummyMemberUrl, httpOptions);
    }
};
