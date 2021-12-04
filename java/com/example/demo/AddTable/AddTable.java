package com.example.demo.AddTable;

import javax.sql.DataSource;

public interface AddTable {
	public boolean AddNewTable(DataSource datasource, String project, String table)throws Exception;
	
	public boolean AddAllTables(DataSource datasource, String project)throws Exception;

}
