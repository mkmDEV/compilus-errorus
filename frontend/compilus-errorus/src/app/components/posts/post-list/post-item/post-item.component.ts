import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Post } from '../../../../models/Post';
import { PostsService } from '../../../../services/posts.service';

@Component({
    selector: 'app-post-item',
    templateUrl: './post-item.component.html',
    styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit {
    @Input() post: Post;
    @Output() votedUp = new EventEmitter<Post>();

    constructor(private postService: PostsService) {
    }

    ngOnInit() {
    }

    onVoteUp() {
        this.post.likes += 1;
        this.votedUp.emit(this.post);
    }

    onVoteDown() {
        this.post.dislikes += 1;
        this.postService.updatePost(this.post).subscribe(post => console.log(post));
    }

    onDelete(post: Post) {
        // delete photo
        this.postService.deletePost(post).subscribe(() => location.reload());
    }

    onEnter(post: Post) {
        const message = document.getElementById('' + post.id);
        message.setAttribute('contenteditable', 'false');
        this.post.message = message.textContent;
        this.postService.updatePost(this.post).subscribe(updatedPost => location.reload());
    }

    onEdit(post: Post) {
        const message = document.getElementById('' + post.id);
        message.setAttribute('contenteditable', 'true');
        message.classList.add('amend-message');
    }

}
