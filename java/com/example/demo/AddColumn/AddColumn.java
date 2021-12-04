package com.example.demo.AddColumn;

import javax.sql.DataSource;

public interface AddColumn {

	public boolean AddNewColumn(DataSource datasource, String project, String table, String column)throws Exception;
	public boolean AddAllColumns(DataSource datasource, String project, String table)throws Exception;

}
