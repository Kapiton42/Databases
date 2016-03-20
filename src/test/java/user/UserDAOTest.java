package user;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by kapiton on 06.03.16.
 */
public class UserDAOTest {

    private static UserDAO userDAO;
    private static Set<User> users;
    private static TestCreateCleanTables testCreateCleanTables;

    @BeforeClass
    public static void setUpDBTestBaseClass() throws Exception {
        ApplicationContext applicationContext = JDBCTestConfig.createApplicationContext();
        testCreateCleanTables = applicationContext.getBean(TestCreateCleanTables.class);
        userDAO = applicationContext.getBean(UserDAO.class);

        testCreateCleanTables.createTable("create-user-tables.sql");
    }

    @Before
    public void setUpTest() {
        users = new HashSet<>();

        User user = User.create("ivan", "ivanov", 100);
        users.add(user);
        user = User.create("petya", "petrov", 300);
        users.add(user);
        users.forEach(userDAO::insert);
    }

    @After
    public void tearDownTest() {
        testCreateCleanTables.cleanTables("users");
    }

    @Test
    public void testInsertGet() throws Exception {
        User user = users.iterator().next();
        assertEquals(user, userDAO.get(user.getId()).get());
    }

    @Test
    public void testGetAll() throws Exception {
        Set<User> usersGet = userDAO.getAll();
        assertEquals(usersGet, users);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = users.iterator().next();
        user.setMoney(500);
        userDAO.update(user);
        assertEquals(user, userDAO.get(user.getId()).get());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testDelete() throws Exception {
        User user = users.iterator().next();
        userDAO.delete(user.getId());
        assertEquals(user, userDAO.get(user.getId()).get());
    }
}