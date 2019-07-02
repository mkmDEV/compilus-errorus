import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    onLogout() {
        sessionStorage.clear();
        this.router.navigateByUrl('').then(() => console.log('logged out'));
    }
}
