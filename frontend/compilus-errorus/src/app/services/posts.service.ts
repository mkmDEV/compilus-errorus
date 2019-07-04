import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest, HttpParams } from '@angular/common/http';
import { Post } from '../models/Post';
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
export class PostsService {
    postsUrl = 'http://localhost:8080/posts';
    memberPostsUrl = 'http://localhost:8080/posts/member-posts';

    constructor(private http: HttpClient) {
    }

    getPosts(): Observable<Post[]> {
        return this.http.get<Post[]>(this.postsUrl, httpOptions);
    }

    getMemberPosts(member: Member): Observable<Post[]> {
        return this.http.post<Post[]>(this.memberPostsUrl, member, httpOptions);
    }

    uploadImage(file: File): Observable<HttpEvent<{}>> {
        const httpOptionsImage = {
            headers: new HttpHeaders({
                Authorization: 'Bearer ' + sessionStorage.getItem('token')
            })
        };
        const formData: FormData = new FormData();
        formData.append('file', file);
        const req = new HttpRequest('POST', 'http://localhost:8080/upload', formData, httpOptionsImage);
        return this.http.request(req);
    }

    savePost(post: Post): Observable<Post> {
        return this.http.post<Post>(this.postsUrl, post, httpOptions);
    }

    updatePost(post: Post): Observable<Post> {
        return this.http.put<Post>(`${ this.postsUrl }/${ post.id }`, post, httpOptions);
    }

    deletePost(post: Post) {
        return this.http.delete(`${ this.postsUrl }/${ post.id }`, httpOptions);
    }
}
