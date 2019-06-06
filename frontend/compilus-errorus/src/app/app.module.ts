import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {PostsComponent} from './components/posts/posts.component';
import {PostItemComponent} from './components/post-item/post-item.component';
import {ContainerComponent} from './components/container/container.component';
import {LeftComponent} from './components/left/left.component';
import {RightComponent} from './components/right/right.component';
import {HeaderComponent} from './components/header/header.component';
import {AddPostComponent} from './components/add-post/add-post.component';
import { EventsComponent } from './components/events/events.component';
import { AdvertComponent } from './components/advert/advert.component';
import { GroupsComponent } from './components/groups/groups.component';

@NgModule({
    declarations: [
        AppComponent,
        PostsComponent,
        PostItemComponent,
        ContainerComponent,
        LeftComponent,
        RightComponent,
        HeaderComponent,
        AddPostComponent,
        EventsComponent,
        AdvertComponent,
        GroupsComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
