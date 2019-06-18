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

    constructor(private postsService: PostsService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    getPosts() {
        this.postsService.getPosts().subscribe(posts => {
            this.posts = posts;
        });
    }

    onVotedUp(post: Post) {
        console.log(post);
        this.postsService.updatePost(post).subscribe(updatedPost => console.log(updatedPost));
    }

}
