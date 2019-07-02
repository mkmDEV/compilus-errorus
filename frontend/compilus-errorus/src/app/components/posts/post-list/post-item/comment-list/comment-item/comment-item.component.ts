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
    @Output() deleted = new EventEmitter<FlComment>();
    @ViewChild('editableMessage', {static: false}) messageSpan;
    currentlyEdited: boolean;
    shakingUp = false;
    shakingDown = false;

    constructor() {
    }

    ngOnInit() {
        this.currentlyEdited = false;
    }

    onVoteUp() {
        this.comment.likes += 1;
        this.updated.emit(this.comment);
        this.shakingUp = true;
        setTimeout(() => this.shakingUp = false, 1000);
    }

    onVoteDown() {
        this.comment.dislikes += 1;
        this.updated.emit(this.comment);
        this.shakingDown = true;
        setTimeout(() => this.shakingDown = false, 1000);
    }

    onEdit() {
        if (this.isUserTheSame()) {
            this.currentlyEdited = true;
        }
    }

    onEnter() {
        this.comment.message = this.messageSpan.nativeElement.textContent;
        this.currentlyEdited = false;
        this.updated.emit(this.comment);
    }

    onDelete() {
        if (this.isUserTheSame()) {
            this.deleted.emit(this.comment);
        }
    }

    isUserTheSame() {
        return this.comment.member.email === sessionStorage.getItem('email');
    }
}
