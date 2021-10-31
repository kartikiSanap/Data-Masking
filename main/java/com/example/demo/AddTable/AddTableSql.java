package com.example.demo.AddTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.AddColumn.AddColumnSql;
import com.example.demo.ShowTables.ShowTablesSql;

public class AddTableSql implements AddTable{
	public boolean AddNewTable(DataSource datasource, String project, String table) throws Exception
	{
		String str = new ShowTablesSql().ShowAllTables( datasource,  project);
			
		if(str.contains(table))
		{	
			String res="";
			String sql = "select project_id from projects where project_name ='"+project+"';";
			try {
				Connection con = datasource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rst = ps.executeQuery();
				rst.next();
		        int project_id=rst.getInt(1);
		        sql ="insert into tableDetails(project_id,table_name) values('"+project_id+"','"+table+"');";
				 PreparedStatement ps1 = con.prepareStatement(sql);
				 ps1.execute();
				 con.close ();
				 con = null;
			}
			catch(Exception e)
			{
				throw new Exception(e);
			}
			return true;
			
		}
		else
		{
			
			return false;
		}
			
		
	}
	public boolean AddAllTables(DataSource datasource, String project)throws Exception
	{
		String str[] = new ShowTablesSql().ShowAllTablesV2( datasource,  project).split("\\s+");
		boolean check = true;
		
		for(int i=0; i<str.length; i++)
		{
			AddNewTable( datasource,  project,  str[i]);
			new AddColumnSql().AddAllColumns(datasource, project,str[i]);
			
		}
		return check;
		
	}

}
