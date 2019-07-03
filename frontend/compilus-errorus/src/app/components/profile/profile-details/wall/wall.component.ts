import { Component, OnInit } from '@angular/core';
import { Post } from '../../../../models/Post';
import { PostsService } from '../../../../services/posts.service';
import { Member } from '../../../../models/Member';
import { AuthService } from '../../../../services/auth.service';

@Component({
    selector: 'app-wall',
    templateUrl: './wall.component.html',
    styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {
    member = new Member();
    ownPosts: Post[];

    constructor(private authService: AuthService, private postService: PostsService) {
    }

    ngOnInit() {
        this.authService.getLoggedInMember().subscribe( loggedInMember => {
            this.member = loggedInMember;
            this.getPosts();
        });
    }

    getPosts() {
        this.postService.getLoggedInMemberPosts(this.member).subscribe(posts => {
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
