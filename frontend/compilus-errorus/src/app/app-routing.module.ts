import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsComponent } from './components/events/events.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { GroupsComponent } from './components/groups/groups.component';
import { WelcomeComponent } from './components/welcome/welcome.component';

const routes: Routes = [
    {path: '', component: WelcomeComponent},
    {path: 'home', component: HomeComponent},
    {path: 'events', component: EventsComponent},
    {path: 'profile', component: ProfileComponent},
    {path: 'groups', component: GroupsComponent},
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
