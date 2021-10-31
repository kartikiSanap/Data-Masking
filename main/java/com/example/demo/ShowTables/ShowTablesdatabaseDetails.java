package com.example.demo.ShowTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class ShowTablesdatabaseDetails implements ShowTables{

	@Override
	public String ShowAllTables(DataSource datasource, String project) throws Exception
	{
		String res="";
		String sql = "select project_id from projects where project_name ='"+project+"';";
		try {
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
	        int project_id=rst.getInt(1);
	        sql = "select table_name from tableDetails where project_id="+project_id;
	        PreparedStatement ps1 = con.prepareStatement(sql);
			rst = ps1.executeQuery();
			 
			
			while(rst.next()) {
	            res+=rst.getString(1)+" ";
	           
	         }
			con.close();
			con = null;
	         }
		catch(Exception e)
		{
			throw new Exception(e);
		}
			
		
		return res;
	}
}

