package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;
import com.example.demo.AddColumn.ColumnId;

import com.example.demo.showColumns.ShowColumnsdatabaseDetails;

public class TableMask {

	public static void maskTable(DataSource datasource ,String project,String table,String algo) throws Exception
	{	
		
	
		try
		{	
			
			String columns[] = new ShowColumnsdatabaseDetails().ShowALLColumns(datasource,project,table).split(" ");
			
			int column_id;
			for(int i=0; i<columns.length; i++)
			{
				column_id = new ColumnId().GetColumnId( datasource,  project,  table,  columns[i].split(";")[0]); 
			
				ColumnMask.maskColumn(datasource, column_id,algo);
			}
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
	}
    
}

