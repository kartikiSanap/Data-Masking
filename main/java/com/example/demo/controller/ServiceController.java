package com.example.demo.controller;

import java.sql.Connection;






import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

import com.example.demo.AddColumn.AddColumnSql;
import com.example.demo.AddColumn.ColumnId;
import com.example.demo.AddProject.AddProjects;
import com.example.demo.AddTable.AddTableSql;
import com.example.demo.algorithm.DataMasking;
import com.example.demo.algorithm.maskingNumber;
import com.example.demo.algorithm.maskingString;
import com.example.demo.ShowTables.ShowTablesSql;
import com.example.demo.ShowTables.ShowTablesdatabaseDetails;
import com.example.demo.dao.ColumnMask;
import com.example.demo.dao.ColumnUnmask;

import com.example.demo.dao.TableMask;
import com.example.demo.dao.TableUnmask;

import com.example.demo.showColumns.ShowColumnsSql;
import com.example.demo.showColumns.ShowColumnsdatabaseDetails;

@RestController
@RequestMapping
public class ServiceController {
	
	
	private static final String[] origins = null;
	@Autowired
	DataSource datasource;
	private ArrayList<Object> Project;
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/add/mysql/{project}/{server}/{port}/{user}/{password}")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddProjectMysql(@PathVariable("project") String project, @PathVariable("server") String server, @PathVariable("port") int port, @PathVariable("user")String user,@PathVariable("password") String password) throws Exception
	{
		
		try {
		boolean res = new AddProjects().AddNewProjectMysql( datasource,  project,  "mysql", server,port, user, password);
		// datasource,  project,  "mysql","localhost",3306,"root","976431"
		System.out.println(res);
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
	}
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/add/sql/{project}/{instance_name}")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddProjectSql(@PathVariable("project") String project,@PathVariable("instance_name") String instance_name) throws Exception
	{
	   System.out.println(project+instance_name);
		try {
		boolean b = new AddProjects().AddNewProjectSql( datasource,  project,  "sql", instance_name, "sa","kartiki@13");
		// datasource,  project,  "mysql",LAPTOP-214UVIPL,"sa","976431"
		System.out.println(b);
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
	}

	public String ShowTables(DataSource datasource, String project) throws Exception
	{	
		String res="";
		String res1="";
		
		
		try {
			
		//res = new ShowTablesdatabaseDetails().ShowAllTables(datasource,project);//Show tables which are added in tableDetails
		res1 = new ShowTablesSql().ShowAllTables(datasource, project);			//Show tables which can be added in tableDetails
		
		
		
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		//String temp = "Tables in Primary database\n"+res + "\n\n Tables not added in  Primary database\n" + res1;
		
		return res1;
	}
	public String ShowColumns(DataSource datasource, String project, String table) throws Exception
	{	
		
		String res1="";
		
		
		try {
			
		//Show Columns which are added in columnDetails
		res1 = new ShowColumnsSql().ShowALLColumns(datasource, project,table);			//Show Columns which can be added in columnDetails 
		
		
		
		
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		 
		return res1;
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("api/v1/data/sql")
	@CrossOrigin(origins="http://localhost:4200")
	public List<Project> showProjectList() throws SQLException   //Depending on userinput here we can call projectmask,tablemask or columnmask
	{
		
		Connection conn=datasource.getConnection(); 
		
		List< Project > l=new ArrayList< Project >();
		//String sql="select * from projectdetails";
		String sql="select *from projects";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
	   
	 
		while(rs.next())
		{ 
		    l.add(new Project(rs.getInt("project_id"),rs.getString("project_name")));
		
			
		}
		
	   return l;
		
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("api/v1/data/sql/{proj_name}")
	@CrossOrigin(origins="http://localhost:4200")
	public List<Table> showTableList(@PathVariable String proj_name) throws SQLException   //Depending on userinput here we can call projectmask,tablemask or columnmask
	{
		System.out.println("successful");
		
		Connection conn=datasource.getConnection(); 
		List<Table> l=new ArrayList<Table>();
		try
		{
		String sql="select * from projects inner join tableDetails on projects.project_id=tableDetails.project_id where project_name=?";
		
		
		
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, proj_name);
		ResultSet rs=ps.executeQuery();
	   
		String res=ShowTables(datasource,proj_name);
		
		while(rs.next())
		{ 
			
			Table T1=new Table();
	    	T1.setTablename(rs.getString("table_name"));
	    	T1.setAddingstatus("added");
	    	l.add(T1);
			
		}
		if(res==" ")
		{
			return l;
		}
        String array[]=res.split("\\s+");
		
		
	    for(int i=0;i<array.length;i++)
	    {
	    	Table T1=new Table();
	    	T1.setTablename(array[i]);
	    	T1.setAddingstatus("notadded");
	    	l.add(T1);
	    }
		
		}
		catch(Exception e)
		{
			System.out.println("Exception");
		}
		
		 return l;
		
		
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("api/v1/data/sql/{proj_name}/{table_name}")
	@CrossOrigin(origins="http://localhost:4200")
	public List<Column> showColumnList(@PathVariable String proj_name,@PathVariable String table_name) throws SQLException   //Depending on userinput here we can call projectmask,tablemask or columnmask
	{
		
		Connection conn=datasource.getConnection(); 
		
		List<Column> l=new ArrayList<Column>();
		try
		{
		String sql="select * from columnDetails C inner join tableDetails T on C.table_id=T.table_id inner join projects P on T.project_id=P.project_id where project_name=?  and table_name=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1,proj_name);
		ps.setString(2, table_name);
		ResultSet rs=ps.executeQuery();
	   
	    
		while(rs.next())
		{ 
		    Column c1=new Column();
		    c1.setAddingstatus("added");
		    c1.setName(rs.getString("column_name"));
		    c1.setColumntype(rs.getString("column_type"));
		    c1.setStatus(rs.getString("column_status"));
		    l.add(c1);
		}
		String res=ShowColumns(datasource,proj_name,table_name);
	   
	   if(res==" ")
	   {
		   return l;
	   }
	    String array[]=res.split("\\s+");
	    for(int i=0;i<array.length;i++)
	    {
	    	Column c1=new Column();
	    	String a2[]=array[i].split(";");
	    	String name=a2[0];
	    	String datatype=a2[1];
	    	c1.setName(name);
	    	c1.setStatus("unmasked");
	    	c1.setColumntype(datatype);
	    	c1.setAddingstatus("notadded");
	    	l.add(c1);
	    }
	   
		
		}
		catch(Exception e)
		{
			System.out.println("Exception");
		}
	   return l;
		
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/{project}/{table}/mask/{column}/{algo}")
	@CrossOrigin(origins="http://localhost:4200")
	public void MaskColumn(@PathVariable("project") String project,@PathVariable("table") String table,@PathVariable("column") String column,@PathVariable("algo") String algo)throws Exception
	{
		
		try {
			int column_id = new ColumnId().GetColumnId(datasource, project, table, column);
			ColumnMask.maskColumn(datasource, column_id,algo);
			
         }
		
		
		catch(Exception e)
		{
			throw new Exception(e);
		}
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/{project}/mask/{table}/{algo}")
	@CrossOrigin(origins="http://localhost:4200")
	public void MaskTable(@PathVariable("project") String project,@PathVariable("table") String table,@PathVariable("algo") String algo)throws Exception
	{
		
		
		try {
			TableMask.maskTable( datasource , project, table,algo);
			//ShowColumns(project, table);
			
         }
		
		
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/mask/{project}/{algo}")
	@CrossOrigin(origins="http://localhost:4200")
	public void MaskProject(@PathVariable("project") String project, @PathVariable("algo") String algo)throws Exception
	{
		
		
		try {
			
		String tables[] = new ShowTablesdatabaseDetails().ShowAllTables(datasource,project).split(" ");//Show tables which are added in tableDetails
		for(int i=0; i<tables.length; i++)
		{
			TableMask.maskTable( datasource , project, tables[i],algo);
		}
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/sql/{project}/add/{table}")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddTables(@PathVariable("project") String project,@PathVariable("table") String table) throws Exception
	{	
		
		boolean check;
		try {
		check = new AddTableSql().AddNewTable(datasource, project,table);//return true if added
		System.out.println(check);
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/sql/{project}/addAllTables")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddAllTables(@PathVariable("project") String project)throws Exception
	{
		boolean check;
		try {
			
		check = new AddTableSql().AddAllTables(datasource, project);//return true if added
		System.out.println(check);
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
			
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/{project}/{table}/unmask/{column}")
	@CrossOrigin(origins="http://localhost:4200")
	public void UnmaskColumn(@PathVariable("project") String project,@PathVariable("table") String table,@PathVariable("column")String column)throws Exception
	{
		try {
			int column_id = new ColumnId().GetColumnId(datasource, project, table, column);
			ColumnUnmask.unmaskColumn(datasource, column_id);

         }
		
		
		catch(Exception e)
		{
			throw new Exception(e);
		}
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/{project}/unmask/{table}")
	@CrossOrigin(origins="http://localhost:4200")
	public void UnmaskTable(@PathVariable("project") String project,@PathVariable("table") String table)throws Exception
	{
		try {
			TableUnmask.unmaskTable( datasource , project, table);
			//ShowColumns(project, table);
			
         }
		
		
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("api/v1/data/sql/unmask/{project}")
	@CrossOrigin(origins="http://localhost:4200")
	public void UnmaskProject(@PathVariable("project") String project)throws Exception
	{
		
		try {
			
		String tables[] = new ShowTablesdatabaseDetails().ShowAllTables(datasource,project).split(" ");//Show tables which are added in tableDetails
		for(int i=0; i<tables.length; i++)
		{
			TableUnmask.unmaskTable( datasource , project, tables[i]);
		}
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/sql/{project}/{table}/add/{column}")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddColumns(@PathVariable("project") String project,@PathVariable("table") String table,@PathVariable("column") String column) throws Exception
	{
		
		boolean check;
		try {
		check = new AddColumnSql().AddNewColumn(datasource, project,table, column);//return true if added
		System.out.println(check);
		
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("api/v1/data/sql/{project}/{table}/addAllColumns")
	@CrossOrigin(origins="http://localhost:4200")
	public void AddAllColumns(@PathVariable("project") String project,@PathVariable("table") String table)throws Exception
	{
		boolean check;
		try {
			check = new AddColumnSql().AddAllColumns(datasource, project,table);//return true if added
			System.out.println(check);
			
			}
			catch(Exception e)
			{
				throw new Exception(e);
			}
				
		
	}
	
	
	
}
