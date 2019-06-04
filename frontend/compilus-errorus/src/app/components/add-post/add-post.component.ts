import {Component, OnInit} from '@angular/core';
import {PostsService} from '../../services/posts.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  selectedFile: File;

  constructor(private postsService: PostsService) {
  }

  ngOnInit() {
  }

  onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    this.postsService.uploadImage(this.selectedFile).subscribe();
  }
}
