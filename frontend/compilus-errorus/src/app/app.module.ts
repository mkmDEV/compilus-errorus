import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PostsComponent } from './components/posts/posts.component';
import { PostItemComponent } from './components/posts/post-list/post-item/post-item.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HeaderComponent } from './components/header/header.component';
import { AddPostComponent } from './components/posts/post-list/add-post/add-post.component';
import { EventsComponent } from './components/events/events.component';
import { AdvertComponent } from './components/sidebar/advert/advert.component';
import { GroupsComponent } from './components/groups/groups.component';
import { CommentListComponent } from './components/posts/post-list/post-item/comment-list/comment-list.component';
import { CommentItemComponent } from './components/posts/post-list/post-item/comment-list/comment-item/comment-item.component';
import { PostListComponent } from './components/posts/post-list/post-list.component';
import { AddCommentComponent } from './components/posts/post-list/post-item/comment-list/add-comment/add-comment.component';
import { LatestEventsListComponent } from './components/sidebar/latest-events-list/latest-events-list.component';
import { OwnGroupsListComponent } from './components/sidebar/own-groups-list/own-groups-list.component';
import { EventListComponent } from './components/events/event-list/event-list.component';
import { EventItemComponent } from './components/events/event-list/event-item/event-item.component';
import { AddEventComponent } from './components/events/event-list/add-event/add-event.component';
import { GroupListComponent } from './components/groups/group-list/group-list.component';
import { GroupItemComponent } from './components/groups/group-list/group-item/group-item.component';
import { AddGroupComponent } from './components/groups/group-list/add-group/add-group.component';
import { ProfileComponent } from './components/profile/profile.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileDetailsComponent } from './components/profile/profile-details/profile-details.component';
import { AboutMeComponent } from './components/profile/profile-details/about-me/about-me.component';

@NgModule({
    declarations: [
        AppComponent,
        PostsComponent,
        PostItemComponent,
        SidebarComponent,
        HeaderComponent,
        AddPostComponent,
        EventsComponent,
        AdvertComponent,
        GroupsComponent,
        CommentListComponent,
        CommentItemComponent,
        PostListComponent,
        AddCommentComponent,
        LatestEventsListComponent,
        OwnGroupsListComponent,
        EventListComponent,
        EventItemComponent,
        AddEventComponent,
        GroupListComponent,
        GroupItemComponent,
        AddGroupComponent,
        ProfileComponent,
        HomeComponent,
        ProfileDetailsComponent,
        AboutMeComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
