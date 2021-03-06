package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class UserMapper implements BaseMapper<UserDto> {

    private static final UserMapper instance = new UserMapper();

    private UserMapper(){}

    public static UserMapper getInstance() {
        return instance;
    }

    @Override
    public UserDto resultSetToDto(ResultSet resultSet) throws DaoException {
        UserDto userDto = new UserDto();
        try {
            userDto.setId(resultSet.getLong(ID));
            userDto.setFirstname(resultSet.getString(FIRSTNAME));
            userDto.setUsername(resultSet.getString(USERNAME));
            userDto.setLastname(resultSet.getString(LASTNAME));
            userDto.setEmail(resultSet.getString(EMAIL));
            userDto.setPhoneNumber(resultSet.getString(USER_PHONE_NUMBER_IN_DB));
            userDto.setRoleId(resultSet.getLong(USER_ROLE_ID));
            userDto.setPassword(resultSet.getString(PASSWORD));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userDto;
    }

}
