import { Column } from './../../shared/project.model';
import { ProjectService } from './../../shared/project.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DialogComponent } from './../../shared/dialog.component';
import { MatDialog} from "@angular/material/dialog";
import {MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-columns',
  templateUrl: './columnlist.component.html',
  styleUrls: ['./columnlist.component.css']
})
export class ColumnlistComponent implements OnInit {

  selectedcolumn:string;
  columns: Array<any>;
  proj_name:string;
  table_name:string;
  submitted=false;
 column:string;
  response: any;
      errorMessage: any;
  constructor(private Service : ProjectService,private route: ActivatedRoute,public dialog:MatDialog) { }

  public fun1(status:String)
  {
      if(status=="unmasked")
      {
          return false;
      }
      else{
          return true;
      }
  }
  public fun2(status:String)
  {
      if(status=="masked")
      {
          return false;
      }
      else{
          return true;
      }
  }
  public fun3(addingstatus:string)
  {
    console.log(addingstatus);
       if(addingstatus =="added")
       {
         return true;
       }
       else{
         return false;
       }
  }
  public fun4(addingstatus:string)
  {
    console.log(addingstatus);
       if(addingstatus =="notadded")
       {
         return true;
       }
       else{
         return false;
       }
  }

  onCreate(proj_name:string,table_name:string,column_name:string)
  {  
    const dialogConfig= new MatDialogConfig();
    dialogConfig.width='600px';
    dialogConfig.height='800px';
    dialogConfig.position={
      
      left: '700px',
      right :'400px',
      bottom:'1000px',
      top:'10px'
    
    }
    this.dialog.open(DialogComponent,{width:'600px',height:'800px',data:{'projectname':proj_name,'tablename':table_name,'columnname':column_name}});
     
  }
  unmask(proj_name:string,table_name:string,column_name:string)
  {
      this.Service.unmask_column(proj_name,table_name,column_name).subscribe(
        response => this.response = response,
        error =>  this.errorMessage = <any>error
      );
  
 
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("proj_name");
    const id2 = this.route.snapshot.paramMap.get("table_name");
    this.proj_name=id;
    this.table_name=id2;

    this.Service.getColumns(id,id2).subscribe(data=> {this.columns=data});
  }
  
  onSubmit(formdata)
  {
    this.selectedcolumn=formdata.selectedcolumn;
    this.Service.addcolumn(this.proj_name,this.table_name,this.selectedcolumn).subscribe(
      data=>console.log(data), 
      error => console.log(error)
      );
    
  }

 addallcolumns()
  {
    this.Service.addallcolumns(this.proj_name,this.table_name).subscribe(
      data=>console.log(data), 
      error => console.log(error)
      );
     
  }

  
}
