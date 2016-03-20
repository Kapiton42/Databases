package user;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;
import static java.util.Objects.requireNonNull;
/**
 * Created by kapiton on 06.03.16.
 */

public class UserService {
    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = requireNonNull(userDAO);
    }

    @Transactional
    public void insert(User user) {
        userDAO.insert(user);
    }
    @Transactional
    public Optional<User> get(int userId) {
        return userDAO.get(userId);
    }
    @Transactional
    public Set<User> getAll() {
        return userDAO.getAll();
    }
    @Transactional
    public void update(User user) {
        userDAO.update(user);
    }
    @Transactional
    public void delete(int userId) {
        userDAO.delete(userId);
    }

    @Transactional
    public void withdrawMoneyFromUser(int userId, int money) {
        User user = userDAO.get(userId).get();
        user.setMoney(user.getMoney() - money);
        userDAO.update(user);
    }
}
