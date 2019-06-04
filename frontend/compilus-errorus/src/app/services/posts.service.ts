import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest } from '@angular/common/http';
import { Post } from '../models/Post';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
} 

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  postsUrl:string = 'http://localhost:8080/posts';

  constructor(private http:HttpClient) { }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl);
  }

  savePost() {

  }
  
  uploadImage(file:File): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    const req = new HttpRequest('POST', 'http://localhost:8080/upload', formdata);
    return this.http.request(req);
  }
}

