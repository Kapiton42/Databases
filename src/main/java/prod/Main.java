package prod;

import configuration.BaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import realestate.RealEstate;
import realestate.RealEstateService;
import user.User;
import user.UserService;

/**
 * Created by kapiton on 20.03.16.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(final String... args) {
        ApplicationContext applicationContext = createApplicationContext();

        RealEstateService realEstateService = applicationContext.getBean(RealEstateService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        RealEstate realEstate = new RealEstate("Piter", 0, 300);
        User user = User.create("Ivan", "Ivanov", 500);

        realEstateService.save(realEstate);
        logger.info("real_estate_id {}", realEstate.getId());
        userService.insert(user);

        realEstateService.buyEstate(user.getId(), realEstate.getId());

        realEstate = realEstateService.get(realEstate.getId()).get();
        user = userService.get(user.getId()).get();

        logger.info("User money : {}, RealEstate owner id : {}", user.getMoney(), realEstate.getOwnerId());
    }

    public static ApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(BaseConfiguration.class, ProdConfiguration.class);
    }
}
