import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FlComment } from '../../../../../../models/FlComment';

@Component({
    selector: 'app-comment-item',
    templateUrl: './comment-item.component.html',
    styleUrls: ['./comment-item.component.css']
})
export class CommentItemComponent implements OnInit {
    @Input() comment: FlComment;
    @Output() updated = new EventEmitter<FlComment>();
    @ViewChild('editableMessage', {static: false}) messageSpan;
    currentlyEdited: boolean;

    constructor() {
    }

    ngOnInit() {
        this.currentlyEdited = false;
    }

    onVoteUp() {
        this.comment.likes += 1;
        this.updated.emit(this.comment);
    }

    onVoteDown() {
        this.comment.dislikes += 1;
        this.updated.emit(this.comment);
    }

    onEdit() {
        this.currentlyEdited = true;
    }

    onEnter() {
        this.comment.message = this.messageSpan.nativeElement.textContent;
        this.currentlyEdited = false;
        this.updated.emit(this.comment);
    }

    onDelete() {

    }
}
