package com.example.demo.AddProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

public class AddProjects implements AddProject {
	@Override
	public boolean AddNewProjectMysql(DataSource datasource, String project, String db_type,String server, int port,String user,String password)throws Exception
	{
		String sql ="select count(database_id) from databaseDetails where dbd_name='" + project+"'";
		try
		{	
			
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
			int count = rst.getInt(1);
			if(count>0)
			{
				return false;
			}
			else if(db_type.equals("mysql"))
			{
				
				 String connection_string  = "'jdbc:mysql://"+server+":"+port+"/"+project+","+user+","+password+"'";
				 sql ="insert into databaseDetails(dbd_name,db_type,connection_string) values('"+project+"','mysql',"+connection_string+");";
				 PreparedStatement ps1 = con.prepareStatement(sql);
				 ps1.execute();
				 sql = "select database_id from  databaseDetails where dbd_name='"+project+"';";
				 PreparedStatement ps2 = con.prepareStatement(sql);
				 rst = ps2.executeQuery();
				 rst.next();
				 int database_id = rst.getInt(1);
				 
				 
				 sql="insert into projects(project_name, database_id) values('"+project+"','"+database_id+"');";
				 PreparedStatement ps3 = con.prepareStatement(sql);
				 ps3.execute();
				 
				 sql = "select  connection_string, db_type from databaseDetails where database_id = "+database_id;
				 ps1 = con.prepareStatement(sql);
				 rst = ps1.executeQuery();
				 rst.next();
				 String urlup[] = rst.getString(1).split(",");
				 
				 
				 db_type = rst.getString(2);
				 con.close();
				 if(db_type.equals("mysql"))
				 {
						Class.forName("com.mysql.jdbc.Driver");

				 }
				 else if(db_type.equals("oracle"))
				 {
					 
				 }
				 con = DriverManager.getConnection(urlup[0],urlup[1],urlup[2]);
				 
				 if(con!=null)
				 {
					 System.out.println("connected : "+ con);
				 }
				 else
				 {
					 System.out.println("not connected : "+ con);
				 }
				 return false;
			}
		}catch(Exception e)
		{
			throw new Exception(e);
		}
		return false;
	}
	
	@Override
	public boolean AddNewProjectSql(DataSource datasource, String project, String db_type,String instance_name,String user,String password)throws Exception
	{
		String sql ="select count(database_id) from databaseDetails where dbd_name='" + project+"'";
		try
		{	
			
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
			int count = rst.getInt(1);
			if(count>0)
			{
				return false;
			}
			else if(db_type.equals("sql"))
			{
				
				 String connection_string  = "jdbc:jtds:sqlserver://localhost:1433;instanceName=LAPTOP-GN6TJ4IC" + "\\" + instance_name+";"+ "databaseName=" + project+";"; 
				 System.out.println(connection_string);
				 sql ="insert into databaseDetails(dbd_name,db_type,connection_string) values(?,'sql',?);";
				 PreparedStatement ps1 = con.prepareStatement(sql);
				 ps1.setString(1, project);
				 ps1.setString(2, connection_string);
				 ps1.execute();
				 sql = "select database_id from  databaseDetails where dbd_name='"+project+"';";
				 PreparedStatement ps2 = con.prepareStatement(sql);
				 rst = ps2.executeQuery();
				 rst.next();
				 int database_id = rst.getInt(1);
				 sql="insert into projects(project_name, database_id) values('"+project+"','"+database_id+"');";
				 PreparedStatement ps3 = con.prepareStatement(sql);
				 ps3.execute();
				 
				 /*sql = "select  connection_string , db_type from databaseDetails where database_id = "+database_id;
				 ps1 = con.prepareStatement(sql);
				 rst = ps1.executeQuery();
				 rst.next();
				 String urlup[] = rst.getString(1).split(",");
				 db_type = rst.getString(2);
				 if(db_type.equals("sql"))
				 {
						//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 

				 }
				 else if(db_type.equals("mysql"))
				 {
						Class.forName("com.mysql.jdbc.Driver");

				 }
				 else if(db_type.equals("oracle"))
				 {
					 
				 }
				 con.close();
				 System.out.println(urlup[0] + "  "+urlup[1]+"  "+urlup[2]);
				 Connection con1 = DriverManager.getConnection(urlup[0],urlup[1],urlup[2]);
				 
				 sql = "select * from employee";
				 ps1 = con1.prepareStatement(sql);
				 rst = ps1.executeQuery();
				 rst.next();
				 System.out.println(rst.getString(1)+"  "+rst.getString(2)+"  "+rst.getString(3));
				 
				 

				 con.close();
				  return project;*/
				 
				 
			}
			return true;

		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		
	}
}
