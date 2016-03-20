package prod;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.hibernate.SessionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import utils.HibernateProperties;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by kapiton on 09.03.16.
 */
@Configuration
@EnableTransactionManagement
public class ProdConfiguration {

    @Bean
    Settings settings() {
        final Config config = ConfigFactory.load();
        return new Settings(config);
    }

    @Bean
    String databaseUrl() {
        return settings().databaseUrl();
    }

    @Bean
    String databaseUser() {
        return settings().databaseUser();
    }

    @Bean
    String databasePassword() {
        return settings().databasePassword();
    }

    @Bean
    public DataSource dataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(databaseUrl());
        dataSource.setUser(databaseUser());
        dataSource.setPassword(databasePassword());

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Bean
    public HibernateProperties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", false);
        return new HibernateProperties(properties);
    }
}