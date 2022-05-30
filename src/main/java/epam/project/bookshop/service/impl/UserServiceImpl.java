package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.impl.UserDaoImpl;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.service.UserService;
import lombok.Builder;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Optional;

@Builder
public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static Long ID;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) {
        Optional<User> dao = userDao.findByUsername(login);
        return dao.isPresent();
    }

    @Override
    public boolean add(User user) {
        // todo check to validation
        String password = user.getPassword();

        user.setId(generateID());
        user.setPassword(Arrays.toString(DigestUtils.md5(password)));
        return userDao.save(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public void update(User entity) {

    }

    private Long generateID() {
        return ++ID;
    }

}
