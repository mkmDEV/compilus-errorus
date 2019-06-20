import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Post } from '../../../../models/Post';

@Component({
    selector: 'app-post-item',
    templateUrl: './post-item.component.html',
    styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit {
    @Input() post: Post;
    @Output() updated = new EventEmitter<Post>();
    @Output() deleted = new EventEmitter<Post>();
    @ViewChild('messageText', {static: false}) messageText;
    editable = false;

    constructor() {
    }

    ngOnInit() {
    }

    onVoteUp() {
        this.post.likes += 1;
        this.updated.emit(this.post);
    }

    onVoteDown() {
        this.post.dislikes += 1;
        this.updated.emit(this.post);
    }

    onDelete(post: Post) {
        // delete photo
        this.deleted.emit(post);
    }

    onEnter() {
        this.editable = false;
        this.post.message = this.messageText.nativeElement.textContent;
        this.updated.emit(this.post);

    }

    onEdit() {
        this.editable = true;
    }

}
