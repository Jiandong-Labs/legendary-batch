package com.jiandong.legendarybatch.container;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ImportAutoConfiguration(DataSourceTransactionManagerAutoConfiguration.class)
public class PostgresDataSourceConfig implements PostgresContainerTest {

	@Bean
	DataSource dataSource() {
		return PostgresContainerTest.postgresqlDataSource();
	}

}
