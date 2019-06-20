import { Component, OnInit } from '@angular/core';
import { Post } from '../../../../models/Post';
import { PostsService } from '../../../../services/posts.service';

@Component({
    selector: 'app-wall',
    templateUrl: './wall.component.html',
    styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {
    ownPosts: Post[];

    constructor(private postService: PostsService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    getPosts() {
        this.postService.getLoggedInMemberPosts().subscribe(posts => {
            this.ownPosts = posts;
        });
    }
}
