import { Post } from './Post';
import { FlComment } from './FlComment';
import { FlEvent } from './FlEvent';

export class Member {
    id: string;
    name: string;
    email: string;
    password: string;
    regDate: Date;
    posts: Post[];
    comments: FlComment[];
    events: FlEvent[];

}
