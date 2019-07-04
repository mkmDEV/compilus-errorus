import { Member } from './Member';

export class FlEvent {
    id: number;
    eventTitle: string;
    description: string;
    romanDate: string;
    eventDate: Date;
    creator: Member;
    participants: Member[];
}
