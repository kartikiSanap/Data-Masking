import { Project } from './../../shared/project.model';
import { ProjectService } from './../../shared/project.service';
import { Observable } from 'rxjs';
import { FormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

import { DialogComponent } from './../../shared/dialog.component';
import { MatDialog} from "@angular/material/dialog";
import {MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector:   'app-projectlist',
  templateUrl: './projectlist.component.html',
  styleUrls: ['./projectlist.component.css']
})
export class ProjectlistComponent implements OnInit {

  isPopupOpened=true;
  projects: Array<any>;
  response: any;
  errorMessage: any;
  projectname:string;
  submitted:false;

  servername:string;
  instancename:string;
  port:string;
  username:string;
  password:string;
  constructor( private Service : ProjectService, public dialog : MatDialog ) {}

 
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
  
  onCreate(proj_name:string)
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
    this.dialog.open(DialogComponent,{width:'600px',height:'800px',data:{'projectname':proj_name,'tablename':' ','columnname':' '}});
     
  }
  unmask(proj_name:string)
  {
    this.Service.unmask_project(proj_name).subscribe(
      response => this.response = response,
      error =>  this.errorMessage = <any>error
    );
    
  }
  ngOnInit(): void {
    this.Service.getProjects().subscribe(data=> {this.projects=data});
  }
 

 onSubmit1(formdata)
 {
  this.projectname=formdata.projectname;
  this.username=formdata.username;
  this.password=formdata.password;
 
   
   this.instancename=formdata.instancename;
  
   this.Service.addprojectsql(this.projectname,this.instancename,this.username,this.password).subscribe(
    data=>console.log(data), 
    error => console.log(error)
    );
   
 
 
  
 }
 onSubmit2(formdata)
 {
  this.projectname=formdata.projectname;
  this.username=formdata.username;
  this.password=formdata.password;
  this.servername=formdata.servername;
  this.port=formdata.port;
  this.Service.addprojectmysql(this.projectname,this.servername,this.port,this.username,this.password).subscribe(
    data=>console.log(data), 
    error => console.log(error)
    );

 }
}
  

