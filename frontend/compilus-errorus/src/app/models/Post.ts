import { Member } from './Member';
import { FlComment } from './FlComment';

export class Post {
    id: number;
    message: string;
    postingDate: Date;
    romanDate: string;
    likes: number;
    dislikes: number;
    image: string;
    member: Member;
    postType: string;
    comments: FlComment[];
}
