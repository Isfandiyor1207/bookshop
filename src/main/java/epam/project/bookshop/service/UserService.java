package epam.project.bookshop.service;

import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends GenericService<UserDto> {

    boolean authenticate(Map<String, String> userLogin) throws ServiceException;

    Long findUserRoleByUsername(String username) throws ServiceException;

    Optional<UserDto> findUserByUsername(String username) throws ServiceException;

    boolean updateUserStatusByUserId(Long userId, Long role) throws ServiceException;

    List<UserDto> findAllByUserFields(Map<String, String> userFieldMap) throws ServiceException;

}
