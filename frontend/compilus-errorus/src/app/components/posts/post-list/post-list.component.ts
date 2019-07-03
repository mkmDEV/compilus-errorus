import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/Post';
import { PostsService } from '../../../services/posts.service';
import {Member} from '../../../models/Member';

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

    onUpdated(post: Post) {
        this.postService.updatePost(post).subscribe();
    }

    onDeleted(post: Post) {
        const index = this.posts.indexOf(post);
        this.posts.splice(index, 1);
        this.postService.deletePost(post).subscribe();
    }

    onAdded(newPost: { member: Member, message: string, postType: string, image: File, imageName: string }) {
        const post = new Post();
        post.message = newPost.message;
        post.postType = newPost.postType;
        post.member = newPost.member;

        if (newPost.image == null) {
            this.postService.savePost(post).subscribe({complete: () => this.refreshPosts()});
        } else {
            post.image = newPost.imageName;
            this.postService.uploadImage(newPost.image).subscribe({
                complete: () => {
                    this.postService.savePost(post).subscribe({
                        complete: () => {
                            this.refreshPosts();
                        }
                    });
                }
            });
        }
    }

    private refreshPosts() {
        this.postService.getPosts().subscribe(posts => {
            this.posts = posts;
        });
    }
}
