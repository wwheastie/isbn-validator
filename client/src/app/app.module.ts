import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './component/home/home.component';
import { IsbnListComponent } from './component/isbn-list/isbn-list.component';
import { IsbnListItemComponent } from './component/isbn-list-item/isbn-list-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    IsbnListComponent,
    IsbnListItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
