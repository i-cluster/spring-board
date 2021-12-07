package com.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration			// 자바 기반의 설정 파일
@EnableTransactionManagement			// AOP 트랜잭션 설정
@PropertySource("classpath:/application.properties")		// 참조할 properties 경로 설정
public class DBConfiguration {

	@Autowired			// Bean으로 등록된 인스턴스(객체)를 클래스에 주입
	private ApplicationContext applicationContext;			// 스프링 컨테이너(Spring Container) - Bean의 생성과 사용, 관계, 생성 주기 관리

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	/*
	 	[Connection Pool]
	 	커넥션 객체를 생성해두고, 데이터베이스에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가 돌려받는 방식
	*/
	
	@Bean				// 컨테이너에 의해 관리되는 객체, Configuration 클래스의 메서드 레벨에서만 지정 가능
	@ConfigurationProperties(prefix = "spring.datasource.hikari")			// PropertySource 지정 파일에서 prefix로 시작하는 설정을 읽어 메서드/클래스에 매핑
	public HikariConfig hikariConfig() {			// Hikari CP(Connection Pool 라이브러리) 객체 생성
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {				// DataSource(Connection Pool 인터페이스) 객체 생성
		return new HikariDataSource(hikariConfig());
	}

	@Bean(name = "sqlSession")
	public SqlSessionFactory sqlSessionFactory() throws Exception {			// SqlSessionFactory(데이터베이스 커넥션, SQL 실행) 객체 생성
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();		// MyBatis-스프링 연동 모듈
		factoryBean.setDataSource(dataSource());
		
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));			// 지정 패턴에 포함되는 XML Mapper 인식
		factoryBean.setTypeAliasesPackage("com.board.domain");			// XML parameterType/resultType 기본 패키지 경로 설정('*' 애스터리스크 사용 가능)
		factoryBean.setConfiguration(mybatisConfig());					// mybatis.configuration(Java <-> XML) 등록 Bean을 설정 파일로 지정
		
		return factoryBean.getObject();			// getObject 메서드가 리턴하는 객체 생성
	}

	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {			// SqlSessionTemplate(SQL 실행에 필요한 메서드) 객체 생성
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();			// mybatis.configuration 설정을 Bean으로 등록
	}
	
}
