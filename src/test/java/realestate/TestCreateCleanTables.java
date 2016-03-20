package realestate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kapiton on 19.03.16.
 */
public class TestCreateCleanTables {
    private SessionFactory sessionFactory;

    public TestCreateCleanTables(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void createTable(String scriptPath) {
        String sql = null;
        try {
            sql = utils.Utils.read(scriptPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(sql).executeUpdate();
    }

    @Transactional
    public void cleanTables() {
        final Set<String> tablesNames = sessionFactory.getAllClassMetadata().values().stream()
                .map(classMetadata -> ((AbstractEntityPersister) classMetadata).getTableName())
                .collect(Collectors.toSet());

        final Session session = sessionFactory.getCurrentSession();
        tablesNames.stream()
                .forEach(tableName -> session.createSQLQuery("DELETE FROM " + tableName).executeUpdate());
    }
}
