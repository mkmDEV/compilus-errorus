import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/Post';
import { PostsService } from '../../../services/posts.service';

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
    posts: Post[];

    constructor(private postService: PostsService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    getPosts() {
        this.postService.getPosts().subscribe(posts => {
            this.posts = posts;
        });
    }

    onVoted(post: Post) {
        this.postService.updatePost(post).subscribe(updatedPost => console.log(updatedPost));
    }

    onDeleted(post: Post) {
        this.postService.deletePost(post).subscribe();
    }

    onEdited(post: Post) {
        this.postService.updatePost(post).subscribe();

    }
}
