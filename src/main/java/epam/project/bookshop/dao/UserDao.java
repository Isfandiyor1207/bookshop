package epam.project.bookshop.dao;

import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<UserDto, User>{

    Optional<UserDto> findByUsername(String username) throws DaoException;

    Optional<Long> findUserRoleByUsername(String username) throws DaoException;

    Optional<UserDto> findUserByEmail(String email) throws DaoException;

    Optional<UserDto> findUserByPhoneNumber(String contact) throws DaoException;

    boolean updateUserStatusByUserId(Long userId, Long role) throws DaoException;

    List<UserDto> findAllByUserFields(String query) throws DaoException;

}
