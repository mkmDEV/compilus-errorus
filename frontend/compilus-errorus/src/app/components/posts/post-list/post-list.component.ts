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
        const index = this.posts.indexOf(post);
        this.posts.splice(index, 1);
        this.postService.deletePost(post).subscribe();
    }

    onEdited(post: Post) {
        this.postService.updatePost(post).subscribe();

    }

    onAdded(newPost: {message: string, postType: string, image: File, imageName: string}) {
        const post = new Post();
        post.message = newPost.message;
        post.postType = newPost.postType;

        if (newPost.image == null) {
            this.postService.savePost(post).subscribe({complete: () => this.refreshPosts()});
        } else {
            this.postService.uploadImage(newPost.image).subscribe( { complete: () => {
                post.image = newPost.imageName;
                this.refreshPosts();
                }
            });
        }
    }

    private refreshPosts() {
        this.postService.getPosts().subscribe(posts => this.posts = posts);
    }
}
