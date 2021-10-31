package com.example.demo.showColumns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.example.demo.ShowTables.ShowTablesdatabaseDetails;

public class ShowColumnsSql implements ShowColumns{

	public String ShowALLColumns(DataSource datasource, String project, String table)throws Exception
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
			String exclude[] = new ShowColumnsdatabaseDetails().ShowALLColumns(datasource, project,table).split("\\s+");
			String ex="'"+exclude[0];
			for(int i=1; i<exclude.length; i++)
			{
				ex+="','"+exclude[i];
			}
			ex+="'";
			
			
			
			if(db_type.equals("sql"))
			 {
				sql ="select column_name , data_type from INFORMATION_SCHEMA.COLUMNS w where w.table_name = '"+table+"'and  w.column_name not in ("+ex+");";

			 }
			
			else if(db_type.equals("mysql"))
			 {
					Class.forName("com.mysql.jdbc.Driver");
					sql = "select column_name, data_type from INFORMATION_SCHEMA.COLUMNS w where w.table_schema ='"+project+"' and w.table_name  = '"+table+"' and w.column_name not in ("+ex+");";

			 }
			 else if(db_type.equals("oracle"))
			 {
				 
			 }
			
			Connection con1 = DriverManager.getConnection(url);
			PreparedStatement ps2 = con1.prepareStatement(sql);
			rst = ps2.executeQuery();
			while(rst.next()) {
	            res+=rst.getString(1)+";"+rst.getString(2)+" ";
	           
	         }
			con1.close();
			con1 = null;
			
	         }
		catch(Exception e)
		{
			throw new Exception(e);
		}
			
		
		return res;
	}
	
	
			
			
			
}
