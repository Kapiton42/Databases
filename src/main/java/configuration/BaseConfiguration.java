package configuration;
/**
 * Created by kapiton on 09.03.16.
 */
import javax.sql.DataSource;
import user.UserDAO;
import user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import utils.HibernateProperties;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "realestate" })
public class BaseConfiguration {
    @Bean
    UserDAO userDAO(DataSource dataSource) {
        return new UserDAO(dataSource);
    }

    @Bean
    UserService userService(UserDAO userDAO) {
        return new UserService(userDAO);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, HibernateProperties properties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{"realestate"});
        sessionFactory.setHibernateProperties(properties.getProperties());
        return sessionFactory;
    }
}


