import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../../../models/Post';
import { PostsService } from '../../../../services/posts.service';

@Component({
    selector: 'app-wall',
    templateUrl: './wall.component.html',
    styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {
    @Input() member;
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

    onUpdated(post: Post) {
        this.postService.updatePost(post).subscribe();
    }

    onDeleted(post: Post) {
        const index = this.ownPosts.indexOf(post);
        this.ownPosts.splice(index, 1);
        this.postService.deletePost(post).subscribe();
    }
}
