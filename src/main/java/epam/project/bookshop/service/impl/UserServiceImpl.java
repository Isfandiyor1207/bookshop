package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.impl.UserDaoImpl;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.validation.BaseValidation;
import epam.project.bookshop.validation.RegistrationValidation;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();
    private UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        Optional<User> dao;
        BaseValidation validation = new BaseValidation();
        if (validation.isEmpty(login) && validation.isEmpty(password)) {
            return false;
        }
        try {
            dao = userDao.findByUsername(login);
            return dao.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(User user) throws ServiceException {
        // todo check to validation email password username phoneNumber

        String password = user.getPassword();

        RegistrationValidation validation=new RegistrationValidation();

        if (!validation.checkEmailValidation(user.getEmail())){ // fixme how Throw Email is not correct to login page from there
            return false;
        }

        if(!validation.checkPasswordToValidation(password)){ // fixme how Throw Email is not correct to login page from there
            return false;
        }


        user.setPassword(Arrays.toString(DigestUtils.md5(password)));
        try {
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

}
