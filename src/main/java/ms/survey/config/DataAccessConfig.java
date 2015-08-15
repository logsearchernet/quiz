package ms.survey.config;

import java.util.Collections;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(PropertyPlaceholderConfig.class)
@EnableTransactionManagement
@Profile("production")
public class DataAccessConfig {
	
	 @Value("${hibernate.show_sql}")
     protected String hibernateShowSql;
     @Value("${hibernate.hbm2ddl.auto}")
     protected String hibernateHbm2DDL;
     @Value("${hibernate.cache.use_second_level_cache}")
     protected String hibernateSecondLevelCache;
     @Value("${hibernate.cache.provider_class}")
     protected String hibernateCacheClass;
     @Value("${hibernate.default_schema}")
     protected String hibernateSchema;
     @Value("${jdbc.driverClassName}")
     protected String jdbcDriver;
     @Value("${jdbc.username}")
     protected String jdbcUsername;
     @Value("${jdbc.password}")
     protected String jdbcPassword;
     @Value("${jdbc.url}")
     protected String jdbcUrl;
	/*
	
	

     @Bean
     public SessionFactory sessionFactory() {

         LocalSessionFactoryBean factoryBean;
         try {
             factoryBean = new LocalSessionFactoryBean();
             Properties pp = new Properties();
             pp.setProperty("hibernate.show_sql", hibernateShowSql);
             pp.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2DDL);
             pp.setProperty("hibernate.cache.use_second_level_cache", hibernateSecondLevelCache);
             pp.setProperty("hibernate.cache.provider_class", hibernateCacheClass);
             pp.setProperty("hibernate.default_schema", hibernateSchema);

             factoryBean.setDataSource(dataSource());
             factoryBean.setPackagesToScan("org.davidmendoza.fileUpload.model");
             factoryBean.setHibernateProperties(pp);
             factoryBean.afterPropertiesSet();
             return factoryBean.getObject();
         } catch (Exception e) {
             //logger.error("Couldn't configure the sessionFactory bean", e);
         	e.printStackTrace();
         }
         throw new RuntimeException("Couldn't configure the sessionFactory bean");
     }
*/
     @Bean
     public DataSource dataSource() {
     	DriverManagerDataSource ds = new DriverManagerDataSource();
         ds.setDriverClassName(jdbcDriver);
         ds.setUsername(jdbcUsername);
         ds.setPassword(jdbcPassword);
         ds.setUrl(jdbcUrl);
         
         Properties connectionProperties = new Properties();
 		connectionProperties.setProperty("hibernate.c3p0.min_size", "5");
 		connectionProperties.setProperty("hibernate.c3p0.max_size", "20");
 		connectionProperties.setProperty("hibernate.c3p0.timeout", "500");
 		connectionProperties.setProperty("hibernate.c3p0.max_statements", "50");
 		connectionProperties.setProperty("hibernate.c3p0.idle_test_period", "200");
 		
 		ds.setConnectionProperties(connectionProperties);					
 		
         return ds;
     }
     
     @Bean
  	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
  		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
  		emf.setPersistenceUnitName("h2Server");
  		emf.setDataSource(dataSource());
  		emf.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
  		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
  		//emf.setJpaPropertyMap(Collections.singletonMap("hibernate.session_factory_name", "mySessionFactory"));
  		
  		return emf;
  	}

	 @Bean
 	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
 		JpaTransactionManager txManager = new JpaTransactionManager();
 		txManager.setEntityManagerFactory(emf);
 		return txManager;
 	}

 	

	/*@Bean
	public DataSource dataSource() {
		//DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:derby://localhost:1527/myDB;create=true", "sa", "sa");
		//dataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
		
		String connectionUrl = rb.getString("connection.url");
		String username = rb.getString("connection.user");
		String password = rb.getString("connection.password");
		DriverManagerDataSource dataSource = new DriverManagerDataSource(connectionUrl);
		dataSource.setDriverClassName(rb.getString("driver.class.name"));
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("hibernate.c3p0.min_size", "5");
		connectionProperties.setProperty("hibernate.c3p0.max_size", "20");
		connectionProperties.setProperty("hibernate.c3p0.timeout", "500");
		connectionProperties.setProperty("hibernate.c3p0.max_statements", "50");
		connectionProperties.setProperty("hibernate.c3p0.idle_test_period", "200");
		
		dataSource.setConnectionProperties(connectionProperties);
		return dataSource;
	}*/

}
