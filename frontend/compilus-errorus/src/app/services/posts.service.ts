import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest, HttpParams } from '@angular/common/http';
import { Post } from '../models/Post';

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
    loggedInMemberPostsUrl = 'http://localhost:8080/posts/logged-in-member';

    constructor(private http: HttpClient) {
    }

    getPosts(): Observable<Post[]> {
        return this.http.get<Post[]>(this.postsUrl, httpOptions);
    }

    getLoggedInMemberPosts(): Observable<Post[]> {
        return this.http.get<Post[]>(this.loggedInMemberPostsUrl, httpOptions);
    }

    uploadImage(file: File): Observable<HttpEvent<{}>> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        const req = new HttpRequest('POST', 'http://localhost:8080/upload', formData, httpOptions);
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

    getComments(postId: number): Observable<Comment[]> {
        const params = new HttpParams().set('postId', String(postId));
        return this.http.get<Comment[]>('http://localhost:8080/comments', {params});
    }
}
