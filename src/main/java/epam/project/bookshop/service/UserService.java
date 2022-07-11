package epam.project.bookshop.service;

import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.ServiceException;

import java.util.Map;

public interface UserService extends GenericService<UserDto> {

    boolean authenticate(Map<String, String> userLogin) throws ServiceException;

    Long findUserRoleByUsername(String username) throws ServiceException;
}
