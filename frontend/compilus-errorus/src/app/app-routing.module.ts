import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsComponent } from './components/events/events.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { GroupsComponent } from './components/groups/groups.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { AuthGuard } from './services/auth-guard';

const routes: Routes = [
    {path: '', component: WelcomeComponent},
    {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
    {path: 'events', component: EventsComponent, canActivate: [AuthGuard]},
    {path: 'profile/:id', component: ProfileComponent, canActivate: [AuthGuard]},
    {path: 'groups', component: GroupsComponent, canActivate: [AuthGuard]},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
