import { Member } from './Member';

export class FlEvent {
    id: number;
    eventTitle: string;
    description: string;
    eventDate: Date;
    creator: Member;
}
