import { Member } from './Member';

export class FlEvent {
    id: number;
    eventTitle: string;
    eventDescription: string;
    eventDate: Date;
    creator: Member;
}
