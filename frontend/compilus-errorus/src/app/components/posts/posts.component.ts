import {Component, OnInit} from '@angular/core';
import {Post} from '../../models/Post';
import {PostsService} from '../../services/posts.service';


@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
    posts: Post[];

    constructor(private postsService: PostsService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    getPosts() {
        this.postsService.getPosts().subscribe(posts => {
            this.posts = posts;
            console.log(posts);
            this.postsService.getComments(3).subscribe(comment => console.log(comment));
        });
    }

}
