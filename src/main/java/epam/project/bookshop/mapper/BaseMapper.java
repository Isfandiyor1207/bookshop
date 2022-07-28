package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.GenericDto;
import epam.project.bookshop.exception.DaoException;

import java.sql.ResultSet;

public interface BaseMapper<T extends GenericDto>{
    T resultSetToDto(ResultSet resultSet) throws DaoException;
}
