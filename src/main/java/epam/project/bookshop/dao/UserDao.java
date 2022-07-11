package epam.project.bookshop.dao;

import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<UserDto, User>{

    Optional<UserDto> findByUsername(String username) throws DaoException;

    Optional<Long> findUserRoleByUsername(String username) throws DaoException;

    Optional<UserDto> findUserByEmail(String email) throws DaoException;

    Optional<UserDto> findUserByPhoneNumber(String contact) throws DaoException;

}
