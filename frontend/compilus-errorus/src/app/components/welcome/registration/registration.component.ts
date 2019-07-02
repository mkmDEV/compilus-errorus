import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../../../services/registration.service';
import { Member } from '../../../models/Member';

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
    name = '';
    email = '';
    password = '';
    member = new Member();

    constructor(private registrationService: RegistrationService) {
    }

    ngOnInit() {
    }

    onSubmitRegistration() {
        this.member.name = this.name;
        this.member.email = this.email;
        this.member.password = this.password;
        this.registrationService.registerUser(this.member).subscribe();
    }
}
