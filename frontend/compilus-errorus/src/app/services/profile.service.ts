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
    memberUrl = 'http://localhost:8080/members';

    constructor(private http: HttpClient) {
    }

    getFriends(member: Member): Observable<Member[]> {
        return this.http.post<Member[]>(this.memberUrl, member, httpOptions);
    }

    updateMember(member: Member, friend: Member) {
        return this.http.put<Member>(`${this.memberUrl}/member/${member.id}`, friend, httpOptions);
    }
}
