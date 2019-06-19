import { Post } from './Post';
import { FlComment } from './FlComment';
import { FlEvent } from './FlEvent';

export class Member {
    id: string;
    name: string;
    password: string;
    email: string;
    regDate: Date;
    posts: Post[];
    comments: FlComment[];
    events: FlEvent[];
    friends: Member[];
}
