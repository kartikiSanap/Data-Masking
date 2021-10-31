package com.example.demo.ShowTables;

import javax.sql.DataSource;

public interface ShowTables {

	public String ShowAllTables(DataSource datasource, String project) throws Exception;
}
