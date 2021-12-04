package com.example.demo.ShowTables;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class ShowTablesSql  implements ShowTables{
	@Override
	public String ShowAllTables(DataSource datasource, String project) throws Exception
	{
		String res="";
		
		String sql = "select database_id from projects where project_name ='"+project+"';";
		try {
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();

	        int database_id=rst.getInt(1);
	        sql = "select connection_string, db_type from databaseDetails where database_id="+database_id;
	        PreparedStatement ps1 = con.prepareStatement(sql);
			rst = ps1.executeQuery();
			rst.next();
			
			String url = rst.getString(1);
			String db_type = rst.getString(2);
			con.close();
			con = null;
			String exclude[] = new ShowTablesdatabaseDetails().ShowAllTables(datasource, project).split("\\s+");
			String ex="'"+exclude[0];
			for(int i=1; i<exclude.length; i++)
			{
				ex+="','"+exclude[i];
			}
			ex+="'";
			if(db_type.equals("sql"))
			 {
				sql ="select w.table_name from (SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE') w where w.table_name not in ("+ex+");";

			 }
			
			else if(db_type.equals("mysql"))
			 {
					Class.forName("com.mysql.jdbc.Driver");
					sql = "select w.table_name from (SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' and  table_schema = '"+project+"') w where w.table_name not in ("+ex+");";

			 }
			 else if(db_type.equals("oracle"))
			 {
				 
			 }
			
			
			Connection con1 = DriverManager.getConnection(url);
			PreparedStatement ps2 = con1.prepareStatement(sql);
			rst = ps2.executeQuery();
			while(rst.next()) {
	            res+=rst.getString(1)+" ";
				
	           
	         }
			
	         }
		catch(Exception e)
		{
			throw new Exception(e);
		}
			
		
		return res;
	}
	public String ShowAllTablesV2(DataSource datasource, String project) throws Exception
	{
		String res="";
		
		String sql = "select database_id from projects where project_name ='"+project+"';";
		try {
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();

	        int database_id=rst.getInt(1);
	        
	        
	        sql = "select connection_string, db_type from databaseDetails where database_id="+database_id;
	        PreparedStatement ps1 = con.prepareStatement(sql);
			rst = ps1.executeQuery();
			rst.next();
			
			String url = rst.getString(1);
			String db_type = rst.getString(2);
			con.close();
			con = null;
			
			if(db_type.equals("sql"))
			 {
				sql ="SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE'";

			 }
			
			else if(db_type.equals("mysql"))
			 {
					Class.forName("com.mysql.jdbc.Driver");
					sql = "SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' and  table_schema = '"+project+"'";

			 }
			 else if(db_type.equals("oracle"))
			 {
				 
			 }
			
			
			Connection con1 = DriverManager.getConnection(url);
			PreparedStatement ps2 = con1.prepareStatement(sql);
			rst = ps2.executeQuery();
			while(rst.next()) {
	            res+=rst.getString(1)+" ";
	           
	         }
			con1.close();
			
	         }
		catch(Exception e)
		{
			throw new Exception(e);
		}
			
		
		return res;
	}
}

