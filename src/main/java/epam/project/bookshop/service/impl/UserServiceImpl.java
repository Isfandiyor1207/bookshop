package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.impl.UserDaoImpl;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.util.PasswordEncoder;
import epam.project.bookshop.validation.BaseValidation;
import epam.project.bookshop.validation.UserValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final BaseValidation baseValidation = BaseValidation.getInstance();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserValidation userValidation = UserValidation.getInstance();
    private static final PasswordEncoder passwordEncoder=PasswordEncoder.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(Map<String, String> userLogin) throws ServiceException {

        String username = userLogin.get(USERNAME);
        String password = userLogin.get(PASSWORD);


        BaseValidation validation = BaseValidation.getInstance();
        if (validation.isEmpty(username) || validation.isEmpty(password)) {
            logger.info("user in validation ");
            return false;
        }

        try {
            boolean isAuthenticated = true;

            Optional<UserDto> dao = userDao.findByUsername(username);
            if (dao.isPresent()) {

                System.out.println(dao.get());
                UserDto user = dao.get();

                if(!passwordEncoder.checkPassword(password, user.getPassword())) {
                    userLogin.put(WORN_LOGIN, ERROR_LOGIN_MSG);
                    isAuthenticated = false;
                }

                return isAuthenticated;
            } else {
                userLogin.put(WORN_USER, ERROR_USER_NOT_EXIST_MSG);
                System.out.println("service false");
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long findUserRoleByUsername(String username) throws ServiceException {
        try {
            Optional<Long> roleByUsername = userDao.findUserRoleByUsername(username);
            return roleByUsername.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<UserDto> findUserByUsername(String username) throws ServiceException {
        try {
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserStatusByUserId(Long userId, Long role) throws ServiceException {
        try {
            return userDao.updateUserStatusByUserId(userId, role);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> findAllByUserFields(Map<String, String> userFieldMap) throws ServiceException {

        Map<String, String> queryMap=new HashMap<>();

        userValidation.createSearchingQuery(userFieldMap, queryMap);

        StringBuilder stringBuilder = new StringBuilder();

        queryMap.forEach((key, value) -> stringBuilder.append(key)
                .append(" like ")
                .append(value)
                .append(" and "));

        logger.info("Query: " + queryMap);

        if (!userFieldMap.get(USER_ROLE_ID_IN_DB).isEmpty()){
            stringBuilder.append(USER_ROLE_ID_IN_DB).append(" = ").append(userFieldMap.get(USER_ROLE_ID_IN_DB)).append(" and ");
        }

        stringBuilder.append(" deleted = ").append("false");

        String queryString = stringBuilder.toString();

        logger.info("query : " + queryString);

        try {
            return userDao.findAllByUserFields(queryString);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {

        if (!userValidation.userRegistrationValidation(userData)) {
            logger.info("user is not registered his information is invalid");
            return false;
        }

        try {
            Optional<UserDto> optionalUser = userDao.findByUsername(userData.get(USERNAME));
            if (optionalUser.isPresent()) {
                logger.info(optionalUser.get().getUsername() + " is available in database");
                userData.put(WORN_USERNAME, ERROR_USERNAME_MSG);
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        // todo to do hex password
        User user = new User();
        user.setFirstName(userData.get(FIRSTNAME));
        user.setLastName(userData.get(LASTNAME));
        user.setUsername(userData.get(USERNAME));
        user.setEmail(userData.get(EMAIL));
        user.setPassword(passwordEncoder.encode(userData.get(PASSWORD)));
        user.setPhoneNumber(userData.get(PHONE_NUMBER));

        try {
            return userDao.save(user) != null;
        } catch (DaoException e) {
            logger.error("User is not added to database.");
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
    public boolean update(Map<String, String> update) throws ServiceException {

        Map<String, String> query = new HashMap<>();

        boolean isValid = userValidation.checkUpdateUser(update, query);

        if (!isValid) {
            logger.info("user update values is not valid:");
            update.put(WORN_USER_UPDATE, ERROR_USER_UPDATE);
            return false;
        }

        boolean repeatedValidation=true;

        if (!baseValidation.isEmpty(update.get(USERNAME))) {
            try {
                Optional<UserDto> optionalUser = userDao.findByUsername(update.get(USERNAME));
                if (optionalUser.isPresent()) {
                    update.put(WORN_USERNAME, ERROR_USERNAME_MSG);
                    logger.info(WORN_USERNAME + ERROR_USERNAME_MSG);
                    repeatedValidation = false;
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }

        if (!baseValidation.isEmpty(update.get(EMAIL))) {
            try {
                Optional<UserDto> optionalUser = userDao.findUserByEmail(update.get(EMAIL));
                if (optionalUser.isPresent()) {
                    update.put(WORN_EMAIL, ERROR_EMAIL_ALREADY_EXIST);
                    logger.info(WORN_EMAIL + ERROR_EMAIL_ALREADY_EXIST);
                    repeatedValidation = false;
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }


        if (!baseValidation.isEmpty(update.get(PHONE_NUMBER))){
            try {
                Optional<UserDto> optionalUser = userDao.findUserByPhoneNumber(update.get(PHONE_NUMBER));
                if (optionalUser.isPresent()) {
                    update.put(WORN_PHONE_NUMBER, ERROR_PHONE_NUMBER_ALREADY_EXIST);
                    logger.info(WORN_PHONE_NUMBER + ERROR_PHONE_NUMBER_ALREADY_EXIST);
                    repeatedValidation = false;
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }

        if (!repeatedValidation){
            return repeatedValidation;
        }

        StringBuilder stringBuilder = new StringBuilder();

        query.forEach((key, value) -> stringBuilder.append(key)
                .append("='")
                .append(value)
                .append("', "));

        String queryString = stringBuilder.toString();

        try {
            return userDao.updated(queryString, Long.valueOf(update.get(ID)));
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<UserDto> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }


}
