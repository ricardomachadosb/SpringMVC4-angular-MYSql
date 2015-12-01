package com.democratic.restaurant.config;
 
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
/**
 * @author Ricardo Machado
 *
 */
@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan( basePackages = {"com.democratic.restaurant"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {
 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	/**
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/democratic_restaurant");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		
		return dataSource;
	}
	
   @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.democratic.restaurant.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
   
   private Properties hibernateProperties() {
       Properties properties = new Properties();
       properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
       properties.put("hibernate.show_sql", true);
       properties.put("hibernate.format_sql", true);
       return properties;        
   }
   
   @Bean
   @Autowired
   public HibernateTransactionManager transactionManager(SessionFactory s) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(s);
      return txManager;
   }
 
}