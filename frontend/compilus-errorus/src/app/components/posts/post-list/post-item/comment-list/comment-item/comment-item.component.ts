import { Component, Input, OnInit } from '@angular/core';
import { FlComment } from '../../../../../../models/FlComment';

@Component({
  selector: 'app-comment-item',
  templateUrl: './comment-item.component.html',
  styleUrls: ['./comment-item.component.css']
})
export class CommentItemComponent implements OnInit {
    @Input() comment: FlComment;

  constructor() { }

  ngOnInit() {
  }

}
