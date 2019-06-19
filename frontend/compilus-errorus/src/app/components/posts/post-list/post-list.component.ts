import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/Post';
import { PostsService } from '../../../services/posts.service';
import { Member } from '../../../models/Member';

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
    posts: Post[];
    loggedInMember: Member;

    constructor(private postService: PostsService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    getPosts() {
        this.postService.getPosts().subscribe(posts => {
            this.posts = posts;
            this.loggedInMember = this.posts[1].member;
            console.log(this.loggedInMember);
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

    onAdded(newPost: {message: string, postType: string, image: File, imageName: string}) {
        const post = new Post();
        post.message = newPost.message;
        post.postType = newPost.postType;

        if (newPost.image == null) {
            this.posts.push(post);
            this.postService.savePost(post).subscribe({
                complete: () => {
                    post.member = this.loggedInMember;
                    this.posts.push(post);
                }
            });
        } else {
            this.postService.uploadImage(newPost.image).subscribe( { complete: () => {
                post.image = newPost.imageName;
                this.postService.savePost(post).subscribe({complete: () => {
                        post.member = this.loggedInMember;
                        this.posts.push(post);
                    }
                });
                }
            });
        }
    }
}
