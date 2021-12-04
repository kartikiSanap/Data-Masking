package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.example.demo.AddColumn.ColumnId;
import com.example.demo.showColumns.ShowColumnsdatabaseDetails;

public class TableUnmask {
	
	
	public static void unmaskTable(DataSource datasource ,String project,String table) throws Exception
	{	
		
	
		try
		{	
			
			String columns[] = new ShowColumnsdatabaseDetails().ShowALLColumns(datasource,project,table).split(" ");
			int column_id;
			for(int i=0; i<columns.length; i++)
			{
				column_id = new ColumnId().GetColumnId( datasource,  project,  table,  columns[i].split(";")[0]); 
				ColumnUnmask.unmaskColumn(datasource, column_id);
			}
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}

}

