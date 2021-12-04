package com.example.demo.AddColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.example.demo.showColumns.ShowColumnsdatabaseDetails;

public class ColumnDataType {
	 public static String GetDT(DataSource datasource, String project, String table, String column)throws Exception
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
					sql ="select data_type from INFORMATION_SCHEMA.COLUMNS w where w.table_name = '"+table+"'and  w.column_name ='"+column+"'";

				 }
				
				else if(db_type.equals("mysql"))
				 {
						Class.forName("com.mysql.jdbc.Driver");
						sql = "select data_type from INFORMATION_SCHEMA.COLUMNS w where w.table_schema ='"+project+"' and w.table_name  = '"+table+"' and w.column_name ='"+column+"'";

				 }
				 else if(db_type.equals("oracle"))
				 {
					 
				 }
				
				Connection con1 = DriverManager.getConnection(url);
				PreparedStatement ps2 = con1.prepareStatement(sql);
				rst = ps2.executeQuery();
				rst.next();
				
		        res =  rst.getString(1);
		        con1.close();
				con1 = null;
				return res;
		         }
			catch(Exception e)
			{
				throw new Exception(e);
			}
				
			
	}}
