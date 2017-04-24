package gm;


import gm.dao.CustomerDAO;
import gm.service.CustomerService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@Configuration
@PropertySource(value= "classpath:db.properties")
public class CMSConfig {

    @Value("${driver}")
	private String driver;
    @Value("${url}")
	private String url;
    @Value("${userName}")
	private String userName = "root";
    @Value("${password}")
	private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }
//	@Bean
//	public BasicDataSource dataSource(){
//		BasicDataSource ds = new BasicDataSource();
//		ds.setDriverClassName(driver);
//		ds.setUrl(url);
//		ds.setUsername(userName);
//		ds.setPassword(password);
//
//		return ds;
//	}

	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(userName);
		ds.setPassword(password);

		return ds;
	}

	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template = new JdbcTemplate(dataSource());
		return template;
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSource());
		
		return tx;
	}

	@Bean
	public CustomerService customerService(){return new CustomerService();}
	@Bean
	public CustomerDAO customerDAO(){return new CustomerDAO();}

}
