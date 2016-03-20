package user;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by kapiton on 19.03.16.
 */
public class TestCreateCleanTables {
    private JdbcTemplate jdbcTemplate;

    public TestCreateCleanTables(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTable(String scriptPath) {
        try {
            jdbcTemplate.execute(utils.Utils.read(scriptPath));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {

        }
    }

    public void cleanTables(String name) {
        jdbcTemplate.execute("DELETE FROM " + name);
    }
}
