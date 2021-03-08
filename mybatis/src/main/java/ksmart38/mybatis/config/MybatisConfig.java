package ksmart38.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value = "ksmart38.mybatis.dao", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

	//1.datasource DBCP(Hikari pool 설정)
	/*
	 * @Bean(name="hikariDataSource") public DataSource
	 * datasource(@Qualifier("jasyptStringEncryptor") StringEncryptor
	 * stringEncryptor) {
	 * 
	 * String jdcUrl = stringEncryptor.decrypt(
	 * "vnBTB9R08mquh/I5RXB1kBk/OONLvSRuIR/nddeU5LOLqlhrCPaLbapW7XZi1o8vtAEuZPGpHhx9W+9qlbYcoapT9bJZ4q2Idy24S7jYnotsMbptMkHh8g9rh+gVTTQPARLU6T5xvhw="
	 * ); String userName =
	 * stringEncryptor.decrypt("T2sSJIdXcKsxdSK7z71fzxhfPYaURbck"); String password
	 * = stringEncryptor.decrypt("iukZtrudhySD1gDmPTt8UpPTPFJqtiaT");
	 * 
	 * HikariConfig hikariConfig = new HikariConfig();
	 * hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
	 * hikariConfig.setJdbcUrl(jdcUrl); hikariConfig.setUsername(userName);
	 * hikariConfig.setPassword(password); hikariConfig.setMaximumPoolSize(15);
	 * hikariConfig.setMaxLifetime(1000*60*30); return new
	 * HikariDataSource(hikariConfig); }
	 */
	
	//2.Mybatis 연동을 위한 MybatisSqlSessionFactory 설정
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory mybatisSqlSessionFactory(/* @Qualifier("hikariDataSource") */ DataSource dataSource, ApplicationContext context) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:mapper/**/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("ksmart38.mybatis.domain");
		return sqlSessionFactoryBean.getObject();
	}
	
	//3.Mybatis SqlSessionTemplate 설정
	@Bean(name="mybatiSsqlsSqlSessionTemplate")
	public SqlSessionTemplate mybatissqlsSqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	//4.Mybatis TransactionManager 설정
	@Bean(name="mebatisTransactionManager")
	public PlatformTransactionManager transactionManager(/* @Qualifier("hikariDataSource") */ DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
