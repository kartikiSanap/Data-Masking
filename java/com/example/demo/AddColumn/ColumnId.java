package com.example.demo.AddColumn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

public class ColumnId {
	
	public int GetColumnId(DataSource datasource, String project, String table, String column)throws Exception
	{
		String sql = "select c.column_id from(select t.table_id from Projects p inner join tableDetails t on p.project_id = t.project_id where p.project_name = '"+project+"' and t.table_name ='"+table+"') j inner join columnDetails c on c.table_id = j.table_id  where c.column_name = '"+column+"'";
		try {
		Connection con = datasource.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rst = ps.executeQuery();
		rst.next();
        int column_id=rst.getInt(1);
        con.close();
        return column_id;
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		
		
		
	}

}