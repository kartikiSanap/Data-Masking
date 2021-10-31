package com.example.demo.AddProject;

import javax.sql.DataSource;

public interface AddProject {
	
	public boolean AddNewProjectMysql(DataSource datasource, String project, String db_type,String server, int port,String user,String password)throws Exception;
	public boolean AddNewProjectSql(DataSource datasource, String project, String db_type,String instance_name,String user,String password)throws Exception;
}
