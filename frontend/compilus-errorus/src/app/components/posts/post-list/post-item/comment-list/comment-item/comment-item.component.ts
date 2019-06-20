import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FlComment } from '../../../../../../models/FlComment';

@Component({
  selector: 'app-comment-item',
  templateUrl: './comment-item.component.html',
  styleUrls: ['./comment-item.component.css']
})
export class CommentItemComponent implements OnInit {
    @Input() comment: FlComment;
    @Output() voted = new EventEmitter<FlComment>();

  constructor() { }

  ngOnInit() {
  }

  onVoteUp() {
      this.comment.likes += 1;
      this.voted.emit(this.comment);
  }

  onVoteDown() {
      this.comment.dislikes += 1;
      this.voted.emit(this.comment);
  }
}
