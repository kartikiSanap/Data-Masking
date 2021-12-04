package com.example.demo.showColumns;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class ShowColumnsdatabaseDetails implements ShowColumns{
	public String ShowALLColumns(DataSource datasource, String project, String table)throws Exception
	{
		String res="";
		String sql = "select  table_id , table_name from (select table_id , table_name,  project_name from tableDetails inner join projects on "
				+ "tableDetails.project_id = projects.project_id) w  where w.project_name ='"+project+"' and w.table_name ='"+table+"';";
		try {
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
	        int table_id=rst.getInt(1);
	         sql = "select column_name from columnDetails where  table_id = "+table_id;
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
	public String ShowColumnOption(DataSource datasource,int column_id)throws Exception 
	{
		try {
	         String sql = "select column_status from columnDetails where  column_id = "+column_id;

			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
	        String status =rst.getString(1);
	         
				con.close();
				con = null;
				return status;

				
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
}

