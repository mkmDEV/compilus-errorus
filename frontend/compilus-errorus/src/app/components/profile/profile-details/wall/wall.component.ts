import { Component, OnInit } from '@angular/core';
import { Post } from '../../../../models/Post';
import { PostsService } from '../../../../services/posts.service';
import { Member } from '../../../../models/Member';
import { AuthService } from '../../../../services/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-wall',
    templateUrl: './wall.component.html',
    styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {
    ownPosts: Post[];
    member = new Member();
    id: string;

    constructor(private authService: AuthService, private postService: PostsService, private route: ActivatedRoute) {
        this.route.params.subscribe( params => this.id = params.id);
    }

    ngOnInit() {
        this.member.id = this.id;
        this.authService.getMember(this.member).subscribe( member => {
            this.member = member;
            this.getPosts(member);
        });
    }

    getPosts(member: Member) {
        this.postService.getMemberPosts(member).subscribe(posts => {
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
