import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Member} from '../../../../models/Member';

@Component({
    selector: 'app-add-post',
    templateUrl: './add-post.component.html',
    styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
    @Output() postAdded = new EventEmitter<{ member: Member, message: string, postType: string, image: File, imageName: string }>();
    newPost = {
        member: new Member(),
        message: '',
        postType: '',
        image: null,
        imageName: ''
    };
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
        this.newPost.member.email = sessionStorage.getItem('email');
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
