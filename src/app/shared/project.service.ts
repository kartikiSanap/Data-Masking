import { Project } from './project.model';
import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions={
  headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http : HttpClient) { }

 
public getProjects():Observable<any>
  {
    return this.http.get("http://localhost:9997/api/v1/data/sql");
  }

  public getTables(id:string):Observable<any>
  {
    return this.http.get("http://localhost:9997/api/v1/data/sql/"+id);
  }
  public getColumns(name:string,id:string):Observable<any>
  {
    return this.http.get("http://localhost:9997/api/v1/data/sql/"+name+"/"+id);
  }
  public mask_column(proj_name:string,table_name:string,column_name:string,method_name:string):Observable<any>
  {
    

     return this.http.put("http://localhost:9997/api/v1/data/sql/"+proj_name+"/"+table_name+"/mask/"+column_name+"/"+method_name,column_name);
  }
  public mask_project(proj_name:string,method_name:string):Observable<any>
  {
    return this.http.put("http://localhost:9997/api/v1/data/sql/mask/"+proj_name+"/"+method_name,proj_name);
  }
  public mask_table(proj_name:string,table_name:string,method_name:string):Observable<any>
  {
    return this.http.put("http://localhost:9997/api/v1/data/sql/"+proj_name+"/mask/"+table_name+"/"+method_name,table_name);
  }
  public unmask_project(proj_name:string):Observable<any>
  {
    return this.http.put("http://localhost:9997/api/v1/data/sql/unmask/"+proj_name,proj_name);
  }
  public unmask_column(proj_name:string,table_name:string,column_name:string):Observable<any>
  {
    return this.http.put("http://localhost:9997/api/v1/data/sql/"+proj_name+"/"+table_name+"/unmask/"+column_name,column_name);
  }
  public unmask_table(proj_name:string,table_name:string):Observable<any>
  {
    return this.http.put("http://localhost:9997/api/v1/data/sql/"+proj_name+"/unmask/"+table_name,table_name);
  }
 
  public addalltables(project:string):Observable<any>
  {
    return this.http.post("http://localhost:9997/api/v1/data/sql/"+project+"/addAllTables",project);
  }
  public addallcolumns(project:string,table:string):Observable<any>
  {
    return this.http.post("http://localhost:9997/api/v1/data/sql/"+project+"/"+table+"/addAllColumns",table);
  }
  public addcolumn(project:string,table:string,column:string):Observable<any>
  {
    return this.http.post("http://localhost:9997/api/v1/data/sql/"+project+"/"+table+"/add/"+column,column);
  }
  public addtable(project:string,table:string):Observable<any>
  {
    return this.http.post("http://localhost:9997/api/v1/data/sql/"+project+"/add/"+table,table);
  }
  public addprojectsql(project:string,instance_name:string,user:string,password:string):Observable<any>
  {
    
    return this.http.post("http://localhost:9997/api/v1/data/add/sql/"+project +"/"+instance_name, project);
  }
  public addprojectmysql(project:string,server:string,port:string,user:string,password:string): Observable<any>
  {
     return this.http.post("http://localhost:9997/api/v1/data/add/mysql/"+project,project);
  }


}