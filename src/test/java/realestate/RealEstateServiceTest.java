package realestate;

import configuration.BaseConfiguration;
import user.User;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * Created by kapiton on 09.03.16.
 */
public class RealEstateServiceTest {
    public static RealEstateService realEstateService;
    public static SessionFactory sessionFactory;
    private static Set<RealEstate> realEstates;
    private static TestCreateCleanTables testCreateCleanTables;

    @BeforeClass
    public static void setUp() {
        final ApplicationContext applicationContext = createApplicationContext();
        realEstateService = applicationContext.getBean(RealEstateService.class);
        sessionFactory = applicationContext.getBean(SessionFactory.class);
        testCreateCleanTables = applicationContext.getBean(TestCreateCleanTables.class);

        testCreateCleanTables.createTable("create-user-tables.sql");
        testCreateCleanTables.createTable("create-realestate-tables.sql");
    }

    @Before
    public void setUpTest() {
        realEstates = new HashSet<>();

        RealEstate realEstate = new RealEstate("Moscow", 0, 100);
        realEstates.add(realEstate);
        realEstate = new RealEstate("New-York", 0, 300);
        realEstates.add(realEstate);
        realEstates.forEach(realEstateService::save);
    }

    @After
    public void tearDownTest() {
        testCreateCleanTables.cleanTables();
    }

    @Test
    public void testSave() throws Exception {
        RealEstate realEstate = realEstates.iterator().next();
        assertEquals(realEstate, realEstateService.get(realEstate.getId()).get());
    }

    @Test
    public void testGetAll() throws Exception {
        Set<RealEstate> setGet;
        setGet = realEstateService.getAll();
        assertEquals(setGet, realEstates);
    }

    @Test
    public void testUpdate() throws Exception {
        RealEstate realEstate = realEstates.iterator().next();
        realEstateService.changeCost(realEstate.getId(), 500);
        realEstateService.update(realEstate);
        assertEquals(realEstate, realEstateService.get(realEstate.getId()).get());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testDelete() throws Exception {
        RealEstate realEstate = realEstates.iterator().next();
        realEstateService.delete(realEstate.getId());
        realEstateService.get(realEstate.getId()).get();
    }

    @Test
    public void testBuy() {
        User user = User.create("ivan", "ivanov", 1000);
        realEstateService.getUserService().insert(user);

        RealEstate realEstate = realEstates.iterator().next();

        try {
            realEstateService.buyEstate(user.getId(), realEstate.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        user.setMoney(900);
        realEstate.setOwner(user.getId());

        assertEquals(realEstate, realEstateService.get(realEstate.getId()).get());
        assertEquals(user, realEstateService.getUserService().get(user.getId()).get());
    }

    public static ApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(BaseConfiguration.class, HibernateTestConfig.class);
    }
}