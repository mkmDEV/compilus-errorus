import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Member } from '../models/Member';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
    })
};

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    loginUrl = 'http://localhost:8080/login';
    loggedInMemberUrl = 'http://localhost:8080/members/logged-in-member';
    memberUrl = 'http://localhost:8080/members/member';

    constructor(private http: HttpClient) {
    }

    static isAuthenticated(): boolean {
        return sessionStorage.getItem('token') !== null;
    }

    loginUser(member: Member) {
        return this.http.post<{ roles: [], email: string, token: string }>(this.loginUrl, member, httpOptions);
    }

    getLoggedInMember() {
        const member = new Member();
        member.email = sessionStorage.getItem('email');
        return this.http.post<Member>(this.loggedInMemberUrl, member, httpOptions);
    }

    getMember(member: Member) {
        return this.http.post<Member>(this.memberUrl, member, httpOptions);
    }
}
