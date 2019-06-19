import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-add-post',
    templateUrl: './add-post.component.html',
    styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
    @Output() postAdded = new EventEmitter<{message: string, postType: string, image: File, imageName: string}>();
    newPost = {message: '', postType: '', image: null, imageName: ''};
    selectedFile: File;
    message: string;
    hidden: boolean;

    constructor() {
    }

    ngOnInit() {
        this.hidden = true;
    }

    toggleVisibility() {
        this.hidden = !this.hidden;

    }

    onFileChanged(event: any) {
        this.selectedFile = event.target.files[0];
        this.toggleVisibility();
    }

    onSubmit() {
        this.newPost.message = this.message;
        this.newPost.postType = 'USER';
        if (this.selectedFile != null) {
            this.newPost.image = this.selectedFile;
            this.newPost.imageName = this.selectedFile.name;
        }
        this.message = '';
        this.postAdded.emit(this.newPost);
    }
}
