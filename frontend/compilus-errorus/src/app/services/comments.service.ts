import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FlComment } from '../models/FlComment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  commentsUrl = 'http://localhost:8080/comments';

  constructor(private http: HttpClient) {
  }

  getComments(queryString: string): Observable<FlComment[]> {
    return this.http.get<FlComment[]>(this.commentsUrl + queryString);
  }
}
