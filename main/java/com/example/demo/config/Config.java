package com.example.demo.config;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
	import org.springframework.boot.context.properties.ConfigurationProperties;
	import org.springframework.boot.jdbc.DataSourceBuilder;
	import org.springframework.context.annotation.Bean;

	import org.springframework.context.annotation.Configuration;

	import com.zaxxer.hikari.HikariConfig;
	import com.zaxxer.hikari.HikariDataSource;

	


	@Configuration
	public class Config {

		@Bean(name="hikariPrimaryConfig")
		@ConfigurationProperties(prefix="jdbc.primary")
		public HikariConfig hikariConfig()
		{
			return new HikariConfig();
		}
		
		@Bean(name= "sqlServerDataSource")
		@ConditionalOnProperty(prefix="jdbc.primary",value = "password")
	    public DataSource primaryDataSource(@Qualifier("hikariPrimaryConfig") HikariConfig hikariConfig ) 
	    {
	        
		return new HikariDataSource(hikariConfig);	
	    }
		

}
