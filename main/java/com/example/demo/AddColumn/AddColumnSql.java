package com.example.demo.AddColumn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.example.demo.ShowTables.ShowTablesSql;
import com.example.demo.ShowTables.ShowTablesdatabaseDetails;
import com.example.demo.showColumns.ShowColumnsSql;

public class AddColumnSql implements AddColumn {
	
	public boolean AddNewColumn(DataSource datasource, String project, String table, String column)throws Exception
	{
		String str = new ShowTablesdatabaseDetails().ShowAllTables( datasource,  project);
		if(str.contains(table))
		{
			str = new ShowColumnsSql().ShowALLColumns( datasource,  project, table);
			if(str.contains(column))
			{
				String res="";
				String sql = "select table_id from (select table_id, project_name,table_name  from projects inner join tableDetails on projects.project_id = tableDetails.project_id)w  where w.project_name ='"+project+"' and w.table_name  ='"+table+"';";
				try {
					Connection con = datasource.getConnection();
					PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rst = ps.executeQuery();
					rst.next();
			        int table_id=rst.getInt(1);
			        String data_type = ColumnDataType.GetDT(datasource, project, table, column);
			        sql = "insert into columnDetails (table_id,column_name,column_type,column_status) values("+table_id+",'"+column+"','"+data_type+"','unmasked');";
			        PreparedStatement ps1 = con.prepareStatement(sql);
					ps1.execute();
					con.close();
					con = null;
					return true;
				}
			catch(Exception e)
				{
					throw new Exception(e);
				}
				}
			System.out.println("wrong table");
			return false;
		}
		System.out.println("wrong column");

		return false;
		

		
	}
	public boolean AddAllColumns(DataSource datasource, String project, String table)throws Exception
	{
		String str = new ShowTablesdatabaseDetails().ShowAllTables( datasource,  project);
		boolean check = true;
			str = new ShowColumnsSql().ShowALLColumns( datasource,  project, table);
			String columns[] = str.split("\\s+");
			if(columns[0].equals(""))
			{
				return true;
			}
			String res="";
				try {
					
					for(int i=0; i<columns.length; i++)
					{
						check = check & AddNewColumn(datasource,  project,  table,  columns[i].split(";")[0]);
					}
				}
			catch(Exception e)
				{
					throw new Exception(e);
				}
				
				
				
		
		//System.out.print("wrong"+ project+"  "+ table );
		return true;
			
		}
}
