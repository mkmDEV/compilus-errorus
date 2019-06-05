import { Component, OnInit, Input } from '@angular/core';
import { Post } from '../../models/Post';
import { PostsService } from '../../services/posts.service';
import { Key } from 'ts-keycode-enum';

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit {
  @Input() post: Post;

  constructor(private postService: PostsService) { }

  ngOnInit() {
  }

  voteUp() {
    this.post.likes += 1;
    this.postService.updatePost(this.post).subscribe(post => console.log(post));
  }

  voteDown() {
    this.post.dislikes += 1;
    this.postService.updatePost(this.post).subscribe(post => console.log(post));
  }

  onDelete(post:Post) {
    //delete photo
    this.postService.deletePost(post).subscribe( () => location.reload());
  }

  onEnter() {
    let message = document.querySelector(".message");
    message.setAttribute("contenteditable", "false");
    this.post.message = message.textContent;
    this.postService.updatePost(this.post).subscribe(post => location.reload());
  }

  onEdit(post:Post) {
    let message = document.querySelector(".message");
    message.setAttribute("contenteditable", "true");
    message.classList.add("amend-message")
  }

}