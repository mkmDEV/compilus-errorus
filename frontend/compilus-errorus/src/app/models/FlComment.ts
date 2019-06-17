import { Member } from './Member';
import { Post } from './Post';

export class FlComment {

    id: number;
    message: string;
    postingDate: Date;
    likes: number;
    dislikes: number;
    member: Member;
    post: Post;
}
