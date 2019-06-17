import {Component, OnInit} from '@angular/core';
import {PostsService} from '../../../services/posts.service';
import {PostsComponent} from '../posts.component';

@Component({
    selector: 'app-add-post',
    templateUrl: './add-post.component.html',
    styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

    selectedFile: File;
    message: string;
    username: string;
    image: string;

    constructor(private postsService: PostsService) {
    }

    ngOnInit() {
    }

    toggleVisibility() {
        const uploadDiv = document.querySelector("#add-photo-div");
        uploadDiv.classList.toggle("toggle");
    }

    onImageChoice(event: any) {
        this.toggleVisibility();
    }

    onFileChanged(event: any) {
        this.selectedFile = event.target.files[0];
    }

    onUpload() {
        if (this.selectedFile != null) {
            this.toggleVisibility();
            this.postsService.uploadImage(this.selectedFile).subscribe({complete: () => location.reload()});
        }
    }

    onSubmit() {
        let post;
        if (this.selectedFile != null) {
            post = {
                message: this.message,
                username: this.username,
                image: this.selectedFile.name
            };
            this.postsService.savePost(post).subscribe(post => console.log(post));
            this.onUpload();
        } else {
            post = {
                message: this.message,
                username: this.username
            };
            this.postsService.savePost(post).subscribe({complete: () => location.reload()});
        }
    }
}