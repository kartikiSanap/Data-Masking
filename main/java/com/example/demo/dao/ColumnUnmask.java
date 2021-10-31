package com.example.demo.dao;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.example.demo.AddColumn.ColumnDataType;
import com.example.demo.algorithm.BigDecimalMasking;
import com.example.demo.algorithm.DataMasking;
//import main.Algorithm.DateMasking;
import com.example.demo.algorithm.IntegerMasking;
import com.example.demo.algorithm.LongMasking;
import  com.example.demo.algorithm.maskingDate;
import com.example.demo.algorithm.maskingNumber;
import com.example.demo.algorithm.maskingString;

public class ColumnUnmask {
	

	public static void unmaskColumn(DataSource datasource ,int column_id) throws Exception
	{
		String sql ="select tableDetails.table_id, table_name ,column_name, column_type,column_status,project_id ,algoName  from columnDetails inner  join tableDetails on columnDetails.table_id=tableDetails.table_id where columnDetails.column_id="+column_id;
		try
		{	
			Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rst = ps.executeQuery();
			rst.next();
			int table_id = rst.getInt(1);
			String table_name = rst.getString(2);
			String column_name = rst.getString(3);
			String column_type = rst.getString(4);
			String column_status = rst.getString(5);
			int project_id = rst.getInt(6);
			String algo = rst.getString(7);
			if(column_status.equals("unmasked"))
			{
				con.close();
				return ;
			}
			sql = "select project_name from projects where project_id = "+project_id;
			ps = con.prepareStatement(sql);
			rst = ps.executeQuery();
			rst.next();
			String project_name = rst.getString(1);
			sql = "select keyword from algoKey where algo = '"+algo+"'";
			ps = con.prepareStatement(sql);
			rst = ps.executeQuery();
			rst.next();
			String key = rst.getString(1);
			
			sql = "select connection_string, db_type from databaseDetails inner join projects on databaseDetails.database_id = projects.database_id where  projects.project_id="+project_id;
	        PreparedStatement ps1 = con.prepareStatement(sql);
			rst = ps1.executeQuery();
			rst.next();
			
			String url = rst.getString(1);
			String db_type = rst.getString(2);
			
			if(db_type.equals("sql"))
			 {
				sql = "SELECT  COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE where CONSTRAINT_NAME like '%pk%' and table_name = '"+table_name+"'";

			 }
			
			else if(db_type.equals("mysql"))
			 {
					Class.forName("com.mysql.jdbc.Driver");
					sql = "SELECT  COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE where CONSTRAINT_SCHEMA='"+project_name+"' and CONSTRAINT_NAME like '%primary%' and TABLE_NAME = '"+table_name+"' ";

			 }
			 else if(db_type.equals("oracle"))
			 {
				 
			 }
			
			Connection con1 = DriverManager.getConnection(url);
			PreparedStatement ps2 = con1.prepareStatement(sql);
			rst = ps2.executeQuery();
			rst.next();
			String primary_key = rst.getString(1);	           
	         
			
			
			
			if(primary_key.equals(column_name)||primary_key.equals(""))
			{
				con.close();con1.close();
				System.out.println("No primary key/ primary key can't unmask");
				return ;
			}
			
			if(db_type.equals("sql"))
			 {
				sql = "SELECT  COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE where CONSTRAINT_NAME like '%fk%' and table_name = '"+table_name+"'";

			 }
			
			else if(db_type.equals("mysql"))
			 {
					sql = "SELECT  COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE where CONSTRAINT_SCHEMA='"+project_name+"' and CONSTRAINT_NAME like '"+table_name+"%fk%"+"' and TABLE_NAME = '"+table_name+"' ";

			 }
			 else if(db_type.equals("oracle"))
			 {
				 
			 }
			ps = con1.prepareStatement(sql);
			rst = ps.executeQuery();
			while(rst.next())
			{
				String foreign_key = rst.getString(1);
				if(foreign_key.equals(column_name))
				{	
					System.out.println(" Foreign key can't unmask");
					con.close();con1.close();

					return;
				}
					
				
			}
			
			
			String pk_data_type = ColumnDataType.GetDT(datasource, project_name, table_name, primary_key);
			
			sql = "select "+primary_key+","+column_name +" from "+table_name;
			
			ps = con1.prepareStatement(sql);
			ResultSet rst1 = ps.executeQuery();
			boolean ch = false;
			
			if(column_type.equals("date"))
        	{
				if(pk_data_type.equals("int"))
	            {
					
					while(rst1.next()) 
					{
						int row = rst1.getInt(1);
						String d = rst1.getString(2);
						
						String  md = new maskingDate().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = "+row;
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
						
					}
					if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
				}
	            else if(pk_data_type.equals("varchar"))
	            {
	            	
	            	while(rst1.next()) 
					{
						String row = rst1.getString(1);
						String d = rst1.getString(2);
						
						String  md = new maskingDate().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = '"+row+"'";
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
						
					}
	            	if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
	            }
	            else
	            {
	            	System.out.println("primary key is not an int or string");
	            	
	            }
        	}
			
			else if(column_type.equals("varchar"))
        	{
				if(pk_data_type.equals("int"))
	            {
					while(rst1.next()) 
					{
						int row = rst1.getInt(1);
						String d = rst1.getString(2);
						
						String  md = new maskingString().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = "+row;
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
					}
					if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
					
	            	
	            	
	            }
	            else if(pk_data_type.equals("varchar"))
	            {
	            	while(rst1.next()) 
					{
						String row = rst1.getString(1);
						String d = rst1.getString(2);
						
						String  md = new maskingString().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = '"+row+"'";
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
						
					}
	            	if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
	            }
	            else
	            {
	            	System.out.println("primary key is not an int or string");
	            	
	            }
        	}
			else if(column_type.equals("int"))
        	{
				if(pk_data_type.equals("int"))
	            {
					while(rst1.next()) 
					{
						int row = rst1.getInt(1);
						String d = rst1.getString(2);
						
						String  md = new maskingNumber().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = "+row;
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
						
					}
					if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
					
	            	
	            	
	            }
	            else if(pk_data_type.equals("varchar"))
	            {
	            	while(rst1.next()) 
					{
						String row = rst1.getString(1);
						String d = rst1.getString(2);
						
						String  md = new maskingNumber().mainOperation(d, key, true);
						sql = "update "+ table_name+" set "+ column_name+" = '"+md+"' where "+ primary_key+" = '"+row+"'";
						ps = con1.prepareStatement(sql);
						ps.execute();
						ch = true;
						
					}
	            	if(ch)
					{
						sql = "update columnDetails set  column_status  = 'unmasked' where column_id = "+column_id;
						ps = con.prepareStatement(sql);
						ps.execute();
						
					}
	            }
	            else
	            {
	            	System.out.println("primary key is not an int or string");
	            	
	            }
        	}
			
			con.close();con1.close();
			
			
			
		}catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
}