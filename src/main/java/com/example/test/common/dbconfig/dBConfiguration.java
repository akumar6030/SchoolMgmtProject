package com.example.test.common.dbconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class dBConfiguration {
	// Stage
	@Value("${spring.datasource.url}")
	private String dataSourceURL;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Autowired
	private Environment environment;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		String activeProfile = getActiveProfile();
		log.info("Active profile is = " + activeProfile);
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(dataSourceURL);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;


	}

	@Bean(name="jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name="namedTemplate")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
			@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	private String getActiveProfile() {
		String activeProfile = null;
		try {
			activeProfile = environment.getActiveProfiles()[0];
		} catch (Exception e) {
			log.error("Active Profile Error");
		}
		return activeProfile;
	}

	@Bean(name = "transaction")
	@Autowired
	public DataSourceTransactionManager stageTransaction(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
