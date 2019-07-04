import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../../../services/registration.service';
import { Member } from '../../../models/Member';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';

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
    emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';

    constructor(private registrationService: RegistrationService, private modalService: NgbModal) {
    }

    ngOnInit() {
    }

    onSubmitRegistration() {
        if (!this.isEmailValid()) {
            this.open();
            return;
        }

        this.member.name = this.name;
        this.member.email = this.email;
        this.member.password = this.password;
        this.registrationService.registerUser(this.member).subscribe({
            complete: () => {
                this.name = '';
                this.email = '';
                this.password = '';
            }
        });
    }

    isEmailValid() {
        return this.email.match(this.emailPattern);
    }

    open() {
        const modalRef = this.modalService.open(ModalComponent);
        modalRef.componentInstance.title = 'Invalid email';
        modalRef.componentInstance.message = 'Please enter a valid email address.';
    }
}
