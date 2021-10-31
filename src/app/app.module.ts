import { ProjectlistComponent } from './sqlprojects/projectlist/projectlist.component';
import { ProjectComponent } from './sqlprojects/project/project.component';
import { ProjectService } from './shared/project.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, FormControlDirective } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TablelistComponent} from './sqlprojects/tables/tablelist.component';
import { ColumnlistComponent } from './sqlprojects/columns/columnlist.component'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { Page1Component } from './page1/page1.component';
import { SqlprojectsComponent } from './sqlprojects/sqlprojects.component';
import { DialogComponent } from './shared/dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import { ToastrModule } from 'ngx-toastr';
@NgModule({
  declarations: [
    AppComponent,
    Page1Component,
    SqlprojectsComponent,
    ProjectComponent,
    ProjectlistComponent,
    TablelistComponent,
    ColumnlistComponent,
    DialogComponent
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    HttpClientModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatCardModule,
     ToastrModule 
  
   
  ],
  providers: [ProjectService],
  bootstrap: [AppComponent],
  entryComponents:[DialogComponent]
})
export class AppModule { }
