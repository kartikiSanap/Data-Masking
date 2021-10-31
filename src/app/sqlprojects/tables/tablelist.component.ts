import { Table } from './../../shared/project.model';
import { ProjectService } from './../../shared/project.service';
import { Component, Input,OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DialogComponent } from './../../shared/dialog.component';
import { MatDialog} from "@angular/material/dialog";
import {MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-tables',
  templateUrl: './tablelist.component.html',
  styleUrls: ['./tablelist.component.css']
})
export class TablelistComponent implements OnInit {

  tables: Array<any>;
  proj_name:string;
  response: any;
  errorMessage: any;
  project1: object;
  table : string;
  submitted=false;
  dropdowntable:string;
  selectedtable:string;

  constructor(private Service : ProjectService,private route: ActivatedRoute,public dialog : MatDialog) { }
 
  public fun1(status:string)
  {
      if(status=="unmasked")
      {
          return false;
      }
      else{
          return true;
      }
  }
  public fun2(status:string)
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

  
  onCreate(proj_name:string,table_name:string)
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
    this.dialog.open(DialogComponent,{width:'600px',height:'800px',data:{'projectname':proj_name,'tablename':table_name,'columnname':' '}});
     
  }

  unmask(proj_name:string,table_name:string)
  {
    this.Service.unmask_table(proj_name,table_name).subscribe(
      response => this.response = response,
      error =>  this.errorMessage = <any>error
    );
   
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("proj_name");
    this.proj_name=id;
    this.Service.getTables(id).subscribe(data=> {this.tables=data})
  }
  
 
  onSubmit(formdata){

    this.selectedtable=formdata.selectedtable;
    this.Service.addtable(this.proj_name,this.selectedtable).subscribe(
      data=>console.log(data), 
      error => console.log(error)
      );
    

  }
  
   

  addalltables()
  {
      this.Service.addalltables(this.proj_name).subscribe(
        data=>console.log(data), 
        error => console.log(error)
        );
       
       
      
  }
}
