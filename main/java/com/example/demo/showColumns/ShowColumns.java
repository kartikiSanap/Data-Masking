package com.example.demo.showColumns;

import javax.sql.DataSource;

public interface ShowColumns {
	public String ShowALLColumns(DataSource datasource, String project, String Table)throws Exception;
}

