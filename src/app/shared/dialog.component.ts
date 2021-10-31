import { Project } from './project.model';
import { ProjectService } from './project.service';
import {ToastrService} from 'ngx-toastr';
import { Component, OnInit,Inject,ElementRef } from '@angular/core';

import {MatDialog} from '@angular/material/dialog';
import {MatDialogRef,MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector:   'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  projectname:string;
  tablename:string;
  columnname:string;
  selectedoption:string;
  response: any;
  errorMessage: any;
 
  constructor( private Service : ProjectService,private dialogRef:MatDialogRef<DialogComponent>,@Inject(MAT_DIALOG_DATA) public data: any,private toastr:ToastrService ) { 
 
    this.projectname=data.projectname;
      this.tablename=data.tablename;
      this.columnname=data.columnname;
      this.selectedoption=' ';
  }
  changePosition() {
        this.dialogRef.updatePosition({ top: '0px', left: '50px',bottom:'100px' });
    }
  onSubmit(formdata)
  {
     this.selectedoption=formdata.Method;
     if(this.tablename ==' ' && this.columnname==' ' && this.projectname!=' ')
     {
        this.Service.mask_project(this.projectname,this.selectedoption).subscribe(
          data=>console.log(data), 
          error => console.log(error)
          );
          //this.toastr.success('Project Masked Successfully!', 'Success!!');
          
     }
     if(this.projectname!=' ' && this.tablename!=' ' && this.columnname==' ')
     {
        this.Service.mask_table(this.projectname,this.tablename,this.selectedoption).subscribe(
          data=>console.log(data), 
          error => console.log(error)
          );
          //this.toastr.success('Table Masked Successfully!', 'Success!!');
     }
     if(this.projectname!=' ' && this.tablename!=' ' && this.columnname!=' ' && this.selectedoption!=' ')
     {
      //this.toastr.success('Column Masked Successfully!', 'Success!!');
        this.Service.mask_column(this.projectname,this.tablename,this.columnname,this.selectedoption).subscribe(
          data=>console.log(data), 
          error => console.log(error)
          );
         
     }
     this.dialogRef.close();
  }
  Noclick():void
  {
    this.dialogRef.close();
  }
  ngOnInit(): void {
   
   this.dialogRef.updatePosition ({ left: '100px', top: '10px',bottom:'100px',right:'600px' });
  }

}
