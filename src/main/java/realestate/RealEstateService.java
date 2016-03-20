package realestate;

/**
 * Created by kapiton on 06.03.16.
 */

import user.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;
import static java.util.Objects.requireNonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("RealEstateService")
public class RealEstateService {
    @Autowired
    private RealEstateDAO realEstateDAO;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(RealEstateService.class);

    public RealEstateService(final SessionFactory sessionFactory,
                             final RealEstateDAO realEstateDAO, final UserService userService) {
        this.userService = requireNonNull(userService);
        this.realEstateDAO = requireNonNull(realEstateDAO);
    }

    @Deprecated
    RealEstateService() {}

    public UserService getUserService() {
        return userService;
    }

    @Transactional
    public void save(final RealEstate realEstate) {
        realEstateDAO.insert(realEstate);
    }

    @Transactional
    public Optional<RealEstate> get(final int realEstateId) {
        return realEstateDAO.get(realEstateId);
    }

    @Transactional
    public Set<RealEstate> getAll() {
        return realEstateDAO.getAll();
    }

    @Transactional
    public void update(final RealEstate realEstate) {
        realEstateDAO.update(realEstate);
    }

    @Transactional
    public void changeAddress(final int realEstateId, final String address) {
        final Optional<RealEstate> optionalRealEstate = realEstateDAO.get(realEstateId);
        if (!optionalRealEstate.isPresent()) {
            logger.warn("Try Change address of no existing real estate with id {}",
                        realEstateId);
            return;
        }
        optionalRealEstate.get().setAddress(address);
    }

    @Transactional
    public void changeCost(final int realEstateId, final int cost) {
        final Optional<RealEstate> optionalRealEstate = realEstateDAO.get(realEstateId);
        if (!optionalRealEstate.isPresent()) {
            logger.warn("Try Change cost of no existing real estate with id {}",
                        realEstateId);
            return;
        }
        optionalRealEstate.get().setCost(cost);
    }

    @Transactional
    public void changeOwner(final int realEstateId, final int ownerId) {
        final Optional<RealEstate> optionalRealEstate = realEstateDAO.get(realEstateId);
        if (!optionalRealEstate.isPresent()) {
            logger.warn("Try Change owner of no existing real estate with id {}",
                        realEstateId);
            return;
        }
        optionalRealEstate.get().setOwner(ownerId);
    }

    @Transactional
    public void buyEstate(int userId, int realEstateId) {
        RealEstate realEstate = get(realEstateId).get();
        if(realEstate.getOwnerId() != 0) {
            userService.withdrawMoneyFromUser(realEstate.getOwnerId(), -1 * realEstate.getCost());
        }
        realEstate.setOwner(userId);

        userService.withdrawMoneyFromUser(userId, realEstate.getCost());

        update(realEstate);
    }

    @Transactional
    public void delete(final int realEstateId) {
        realEstateDAO.delete(realEstateId);
    }
}
