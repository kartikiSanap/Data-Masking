import { ProjectlistComponent } from './sqlprojects/projectlist/projectlist.component';
import { SqlprojectsComponent } from './sqlprojects/sqlprojects.component';
import { Page1Component } from './page1/page1.component';
import { TablelistComponent } from './sqlprojects/tables/tablelist.component';
import { ColumnlistComponent } from './sqlprojects/columns/columnlist.component';

 
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path:'', component:Page1Component},
  {path:'api/v1/data', component:SqlprojectsComponent},
  {path:'api/v1/data/sql', component:ProjectlistComponent},
  {path:'api/v1/data/sql/:proj_name',component:TablelistComponent,pathMatch: 'full'},
  {path:'api/v1/data/sql/:proj_name/:table_name',component:ColumnlistComponent,pathMatch: 'full'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
